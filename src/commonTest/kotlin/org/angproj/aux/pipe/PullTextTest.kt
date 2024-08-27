/**
 * Copyright (c) 2024 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
 *
 * This software is available under the terms of the MIT license. Parts are licensed
 * under different terms if stated. The legal terms are attached to the LICENSE file
 * and are made available on:
 *
 *      https://opensource.org/licenses/MIT
 *
 * SPDX-License-Identifier: MIT
 *
 * Contributors:
 *      Kristoffer Paulsson - initial implementation
 */
package org.angproj.aux.pipe

import org.angproj.aux.io.PumpReader
import org.angproj.aux.io.Segment
import org.angproj.aux.io.TypeSize
import org.angproj.aux.util.writeGlyphAt
import org.angproj.aux.util.DataBuffer
import org.angproj.aux.util.Reify
import org.angproj.aux.util.chunkLoop
import kotlin.math.min
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertFailsWith
import kotlin.time.measureTime

const val latinLL = """
Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer auctor nisi eu bibendum sodales. Integer dui nulla, 
gravida sit amet laoreet in, ultricies quis risus. Praesent iaculis fermentum risus non placerat. Phasellus dictum 
quis velit sed fermentum. Vestibulum bibendum ex vitae dolor mollis, vitae tincidunt orci porta. Donec elementum nisl 
semper, euismod elit nec, pharetra neque. Vestibulum luctus in diam sed mattis. Class aptent taciti sociosqu ad 
litora torquent per conubia nostra, per 🤣 inceptos himenaeos. Nullam convallis condimentum massa, nec condimentum 
tortor. Nam vel lectus vitae nisi viverra finibus nec quis ante. Sed diam sem, suscipit ac nibh finibus, semper 
volutpat nulla. Ut at convallis elit.

Aliquam tempus erat erat, in commodo massa molestie non. Nullam malesuada molestie orci eget volutpat. Integer 
volutpat sagittis risus quis malesuada. In hac habitasse platea dictumst. Proin lobortis at leo ut suscipit. Integer 
convallis congue nibh id ultrices. Nunc accumsan ut turpis sed 🤮 tempus. Integer auctor vitae odio sed ullamcorper. 
Donec ac ante libero.

Fusce risus risus, laoreet sit amet ornare vel, dignissim id urna. Vivamus leo nulla, interdum eget semper vitae, 
elementum eget tellus. Mauris sit amet ultrices elit, vel rutrum tellus. Curabitur ac dolor quis arcu tincidunt 
tempus. Sed sed rhoncus metus. Cras velit sapien, luctus vitae 🌍 vehicula a, condimentum id velit. Proin ac nibh 
consequat, tristique eros id, dignissim lacus. Nullam blandit, mauris in ullamcorper semper, mauris nulla porttitor 
velit, eu sodales lectus lacus non diam. Maecenas eu tellus id odio laoreet facilisis.

Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean risus odio, ullamcorper a urna non, sagittis rutrum 
augue. Suspendisse potenti. Proin tristique nisi quam, non ⛲ lacinia nibh facilisis sit amet. Integer pulvinar, urna 
efficitur dignissim luctus, sapien lectus pellentesque ex, in mattis arcu nisl ac metus. Maecenas vel malesuada erat, 
posuere sagittis tortor. Cras at magna quis libero tempor convallis a sed augue. Aliquam ac nulla sed dui porttitor 
vehicula commodo ac lectus. Vestibulum eget lorem sed lorem sagittis ornare vitae non diam. Donec quis mauris sit 
amet lectus convallis maximus quis a felis. Etiam maximus accumsan erat ut viverra.

Nullam quis finibus ipsum, tincidunt pharetra sem. Fusce pulvinar efficitur eleifend. Proin tincidunt auctor 
dictum. Phasellus hendrerit ante sit amet consectetur suscipit. Donec consequat posuere augue, congue interdum dui 
iaculis vitae. Nullam dignissim mi purus, eu euismod 😜 dolor dapibus eget. Pellentesque habitant morbi tristique 
senectus et netus et malesuada fames ac turpis egestas. Mauris auctor fermentum turpis non facilisis. Curabitur ac 
erat sed ex varius suscipit. Duis ut euismod urna. Phasellus elit est, euismod eu dapibus non, fermentum nec purus. 
Maecenas vehicula ligula ac orci sodales fermentum. Suspendisse vel enim in lacus malesuada vulputate lacinia id erat. 
Fusce volutpat hendrerit sapien ut mollis.
"""

const val greekLL = """
Ἐπειδὴ τὸν Ἰουδαίων πρὸς Ῥωμαίους πόλεμον συστάντα μέγιστον οὐ μόνον τῶν καθ' ἡμᾶς, σχεδὸν δὲ καὶ ὧν ἀκοῇ
παρειλήφαμεν ἢ πόλεων πρὸς πόλεις ἢ ἐθνῶν ἔθνεσι συρραγέντων, οἱ μὲν οὐ παρατυχόντες τοῖς πράγμασιν, ἀλλ' ἀκοῇ
συλλέγοντες εἰκαῖα καὶ ἀσύμφωνα διηγήματα σοφιστικῶς ἀναγράφουσιν, οἱ παραγενόμενοι δὲ ἢ κολακείᾳ τῇ πρὸς Ῥωμαίους 
ἢ μίσει τῷ πρὸς Ἰουδαίους καταψεύδονται τῶν πραγμάτων, περιέχει δὲ αὐτοῖς ὅπου μὲν κατηγορίαν ὅπου δὲ ἐγκώμιον τὰ 
συγγράμματα, τὸ δ' ἀκριβὲς τῆς ἱστορίας οὐδαμοῦ, προυθέμην ἐγὼ τοῖς κατὰ τὴν Ῥωμαίων ἡγεμονίαν Ἑλλάδι γλώσσῃ 
μεταβαλὼν ἃ τοῖς ἄνω βαρβάροις τῇ πατρίῳ συντάξας ἀνέπεμψα πρότερον ἀφηγήσασθαι Ἰώσηπος Ματθίου παῖς ἐξ Ἱεροσολύμων 
ἱερεύς, αὐτός τε Ῥωμαίους πολεμήσας τὰ πρῶτα καὶ τοῖς ὕστερον παρατυχὼν ἐξ ἀνάγκης.

γενομένου γάρ, ὡς ἔφην, μεγίστου τοῦδε τοῦ κινήματος ἐν Ῥωμαίοις μὲν ἐνόσει τὰ οἰκεῖα, Ἰουδαίων δὲ τὸ νεωτερίζον 
τότε τεταραγμένοις ἐπανέστη τοῖς καιροῖς ἀκμάζον κατά τε χεῖρα καὶ χρήμασιν, ὡς δι' ὑπερβολὴν θορύβων τοῖς μὲν ἐν 
ἐλπίδι κτήσεως τοῖς δ' ἐν ἀφαιρέσεως δέει γίνεσθαι τὰ πρὸς τὴν ἀνατολήν, ἐπειδὴ Ἰουδαῖοι μὲν ἅπαν τὸ ὑπὲρ Εὐφράτην 
ὁμόφυλον συνεπαρθήσεσθαι σφίσιν ἤλπισαν, Ῥωμαίους δὲ οἵ τε γείτονες Γαλάται παρεκίνουν καὶ τὸ Κελτικὸν οὐκ ἠρέμει, 
μεστὰ δ' ἦν πάντα θορύβων μετὰ Νέρωνα, καὶ πολλοὺς μὲν βασιλειᾶν ὁ καιρὸς ἀνέπειθεν, τὰ στρατιωτικὰ δὲ ἤρα μεταβολῆς 
ἐλπίδι λημμάτων.

ἄτοπον ἡγησάμενος περιιδεῖν πλαζομένην ἐπὶ τηλικούτοις πράγμασι τὴν ἀλήθειαν, καὶ Πάρθους μὲν καὶ Βαβυλωνίους Ἀράβων 
τε τοὺς πορρωτάτω καὶ τὸ ὑπὲρ Εὐφράτην ὁμόφυλον ἡμῖν Ἀδιαβηνούς τε γνῶναι διὰ τῆς ἐμῆς ἐπιμελείας ἀκριβῶς, ὅθεν τε 
ἤρξατο καὶ δι' ὅσων ἐχώρησεν παθῶν ὁ πόλεμος καὶ ὅπως κατέστρεψεν, ἀγνοεῖν δὲ Ἕλληνας ταῦτα καὶ Ῥωμαίων τοὺς μὴ 
ἐπιστρατευσαμένους, ἐντυγχάνοντας ἢ κολακείαις ἢ πλάσμασι.

Καίτοι γε ἱστορίας αὐτὰς ἐπιγράφειν τολμῶσιν, ἐν αἷς πρὸς τῷ μηδὲν ὑγιὲς δηλοῦν καὶ τοῦ σκοποῦ δοκοῦσιν ἔμοιγε 
διαμαρτάνειν. βούλονται μὲν γὰρ μεγάλους τοὺς Ῥωμαίους ἀποδεικνύειν, καταβάλλουσιν δὲ ἀεὶ τὰ Ἰουδαίων καὶ 
ταπεινοῦσιν: οὐχ ὁρῶ δέ, πῶς ἂν εἶναι μεγάλοι δοκοῖεν οἱ μικροὺς νενικηκότες: καὶ οὔτε τὸ μῆκος αἰδοῦνται τοῦ 
πολέμου οὔτε τὸ πλῆθος τῆς Ῥωμαίων καμούσης στρατιᾶς οὔτε τὸ μέγεθος τῶν στρατηγῶν, οἳ πολλὰ περὶ τοῖς Ἱεροσολύμοις 
ἱδρώσαντες οἶμαι ταπεινουμένου τοῦ κατορθώματος αὐτοῖς ἀδοξοῦσιν.

Οὐ μὴν ἐγὼ τοῖς ἐπαίρουσι τὰ Ῥωμαίων ἀντιφιλονεικῶν αὔξειν τὰ τῶν ὁμοφύλων διέγνων, ἀλλὰ τὰ μὲν ἔργα μετ' 
ἀκριβείας ἀμφοτέρων διέξειμι, τοὺς δ' ἐπὶ τοῖς πράγμασι λόγους ἀνατίθημι τῇ διαθέσει καὶ τοῖς ἐμαυτοῦ πάθεσι διδοὺς 
ἐπολοφύρεσθαι ταῖς τῆς πατρίδος συμφοραῖς. ὅτι γὰρ αὐτὴν στάσις οἰκεία καθεῖλεν, καὶ τὰς Ῥωμαίων χεῖρας ἀκούσας καὶ 
τὸ πῦρ ἐπὶ τὸν ναὸν εἵλκυσαν οἱ Ἰουδαίων τύραννοι, μάρτυς αὐτὸς ὁ πορθήσας Καῖσαρ Τίτος, ἐν παντὶ τῷ πολέμῳ τὸν μὲν 
δῆμον ἐλεήσας ὑπὸ τῶν στασιαστῶν φρουρούμενον, πολλάκις δὲ ἑκὼν τὴν ἅλωσιν τῆς πόλεως ὑπερτιθέμενος καὶ διδοὺς τῇ 
πολιορκίαι χρόνον εἰς μετάνοιαν τῶν αἰτίων.
"""

const val chineseLL = """
本格表世向駐供暮基造食四検内協案。山文提議負表崎何九被博特止点関通写覧馬。会出週朝野加交伊再謝神年拡員部禁辺。
府構供投十隊済参国拐政意紛集癒夜治和。陸規地景何守谷困乱青購謝輸。同極価売現近題日稿売報革衛月塁両改。禁消情飯治刊読救南毎番五掲田夫意鈴。
手新市要所由州時青拳数子。党詳半前象写鐘木亡情強万構図天報。🤪

選整由逃済経首釈経抗横点切化注。坂変抑掲購圧学同旅未回詐実府質般万聞少探。喜祖合記提変需全一猛獲大際欧体考商丸口秘。
国社新図聞下著東声学社育鮮政静月報。面別屯並渡指業試祉紙確挙出輪暑野。棋優塁内反徒人政充乗征特者。張音悪教件祭上民始企賞初。
質碁習教村身野退垂花目用下声関帯少。保注北州医界並曜第米先談並。

構細率恒南目現入国政面青紙論。歌展方恥日月第見必編会刷済今浮生生表。要語定応上取用合伊負合和級護特際的。足特逃取広要事活強毎合図料読判。
米曜生択受索写株年点周治生主化菱列。難著悩報暮園勝市報通治者葉郵報写並保営表。抑埼家院話避后質知寺定引亜。未済京笑明話保観難黒毒八模。
反治立本日日側通受新心索。🤔

夫賀変気災建提特祥速豊戦掲現田日証設語前。改参通前因遺販飛合際請共立内博。論誤白授申戦隙住抽容道浴。権所面上選稿喫西権森官帯掲。
実来接計負大管教間線本教退。舵立割将留内件考当一実表警本外決新捕箱稿。験央専九栃側京読一台線害刊多閣宮。変九人曲派物回写隊案死色前備妻搬坂文種。
暮名討意府輪当枠社手光望古大。

疑式楽祐護本躍入形否用上演物。事化個質条特撃話選略供社任。学派検橋鹿顔減著得断文始惑件銀選量。妙浜前療谷達拍年者判防空。
策公社護間善能男身建道学開書右。氏載目聞給米粧断府更意治規景背読始藤帯。玲内沈見機期属君安社遇上予韓区貴二大府。場右髪窮秋示質芸域思受月稼。
浜再藤華何億査外感暫際持岡温黒力講。🤧

入籍抑集家復会済小劇等並紙政付社療球。都能念図取杯断条外三練著年世育体件。論提配彼進稿負転広民事述作人早融戦現経。戻茨置契記無統敬中気文要。
要案民鹿黒那舞開張見心育族連齢考谷起初。報金催木粘武骨館中物紛校図。改読樹図治前利任閉線夫存必習奪合提。権追動確済事則所責百連竹育。
緩館際芸法道投福載聞北本漏小。

挙度工火夕未変本代多購塁東親綱。発浜厚補指七追氷意春田企入断興生死。日載奈館歩確宮並味供赳平詳新投広想頗転。近午部法園楽転施第注載必再相千必債。
失論碁手引完変式旦世質検賛転成。芸重離業大連転突要民流写球。技提喰査都攻査著小止旅闘題今返氏。上見表面呼松下映最田中覧取等。
新群同長労制戦象被争約点出読。🥴

者連監全載問悲口郷区覧池低近先豊門。技転品球禁末海韓千観優応棋本真小。詳新対歴式情後江合申崎雅屋被月吹数産。投父法感木備情関来賀生人。
起説宇立憶再集集酒町指人第転装。決川作仮得退馬元方社門響戦間片代。誰若著院注摯係案混止永目来特無思記小能。望情自体球繁初屋欠株遍撮無。
相属日記芸由治内上真初芸亡。

提調能期点毎重政監竹図問能未作来芸落。画体併州包集読米中航多指増属世。志載弓傳地組青判民小成上美開連適市佐。気思独篠治安門街面応区毅深。
人万必文入左変点彦家量責健件。買軍光案認止量聞政人潟狙消傷府大月生。強押場多園豊料聞争書責監様川紙術本。世長焦属読選和会問便門除首坊漏。
原海備熱真逮計了集図郊前。

無科難朝人止反稿込果者残統聞舞果。提機東境本廟滅売動断機禁碁以辺復首謙。追同航女投代脅営士川白康。果管成最将作数上用化気就。
紙盟護化悼護別場報佐面字法暮。論厳浪権作必秩上貫報極進常止記。線転演江誤守意局面変理文意基明。提年便制入応難苦不習開初勤知周供勝味収前。
戦六利統既鎌江陵機全円株。感金覚品賞変挙上万合参真警特提。
"""

class StringReader(text: String, private val half: Boolean = false) : PumpReader {
    val data = DataBuffer(text.encodeToByteArray())

    override fun read(data: Segment): Int {
        var length  = min(data.limit, this.data.remaining)

        if(half) if (this.data.remaining < (this.data.size / 2)) length /= 2

        var index = chunkLoop<Reify>(0, length, TypeSize.long) {
            data.setLong(it, this.data.readLong())
        }
        index = chunkLoop<Reify>(index, length, TypeSize.byte) {
            data.setByte(it, this.data.readByte())
        }
        return index
    }
}

class PullTextTest {

    /**
     * The goal is to pull all data from the TextSource.
     * */
    @Test
    fun testStreamPull() {

        val text = latinLL + greekLL + chineseLL
        val copy = text.encodeToByteArray()
        val canvas = ByteArray(copy.size)
        var pos = 0


        val readable = Pipe.buildTextPullPipe(StringReader(text)) //PullPipe(Default, PumpSource<TextType>(StringReader(text))).getSink()
        val time = measureTime {
            do {
                val cp = readable.readGlyph()
                pos += canvas.writeGlyphAt(pos, cp)
            } while(pos < canvas.size)
        }
        println(time)
        assertContentEquals(copy, canvas)
        assertFailsWith<UnsupportedOperationException> { readable.readGlyph() }
    }

    @Test
    fun testStreamPullClose() {

        val text = latinLL + greekLL + chineseLL
        val copy = text.encodeToByteArray()
        val canvas = ByteArray(copy.size)
        var pos = 0

        val readable = Pipe.buildTextPullPipe(StringReader(text)) // PullPipe(Default, PumpSource<TextType>(StringReader(text))).getSink()
        do {
            val cp = readable.readGlyph()
            pos += canvas.writeGlyphAt(pos, cp)
        } while(pos < canvas.size / 2)

        readable.close()
        assertFailsWith<UnsupportedOperationException> { readable.readGlyph() }
    }
}