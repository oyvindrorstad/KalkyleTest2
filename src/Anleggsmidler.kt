import kotlin.test.assertEquals

class Anleggsmidler {
    // kalkulerte informasjonsfelt - output
    var grunnlag: Long = -999
    var aaretsAvskrivningOgInntektsforingAvNegativSaldo: Long = -999

    fun beregnAnleggsmidler(skattemelding: Skattemelding ): Long {

        // doc Ref kap 1.1 Kalkyler for anleggsmidler, både avskrivbare og ikke avskrivbare.
        // Nr 1 : "grunnlagForAvskrivningOgInntektsføring"

        // satser og grensesverdier
        val GRENSEVERDI: Long = 14999L
        val sats1: Long = 30 // (gyldig verdi 0 -30%, default 30%
        val sats2: Long = 70
        val sats3: Long = 100
        val sats4: Long = 35
        var sats: Long = 0

        // map skattemeldingsdata (XSD) til kortnavn
        var saldoavskrevetAnleggsmiddelObjektidentifikator: Int = 1
        var saldoavskrevetAnleggsmiddelInngaaendeVerdi: Long =
            skattemelding.data.get("/melding/spesifikasjonAvBalanse/saldoavskrevetAnleggsmiddel/inngaaendeVerdi")!!
        var saldoavskrevetAnleggsmiddelPaakostning: Long =
            skattemelding.data.get("melding/spesifikasjonAvBalanse/saldoavskrevetAnleggsmiddel/paakostning")!!

        // KALKYLER  beregn grunnlagForAvskrivningOgInntektsforing
        grunnlag =
            saldoavskrevetAnleggsmiddelInngaaendeVerdi + saldoavskrevetAnleggsmiddelPaakostning

        // bestem sats ut fra grunnlagForAvskrivningOgInntektsforing
        if (grunnlag > GRENSEVERDI)
            sats = sats1
        if ((grunnlag < GRENSEVERDI) and (grunnlag >= 0))
            sats = sats2
        if ((grunnlag < 0) and (grunnlag > -GRENSEVERDI))
            sats = sats3
        if (grunnlag < 0 - GRENSEVERDI)
            sats = sats4

        aaretsAvskrivningOgInntektsforingAvNegativSaldo = grunnlag * sats / 100
        var saldo: Long = grunnlag - aaretsAvskrivningOgInntektsforingAvNegativSaldo

        // map beregnede kortnavn til output
        skattemelding.data.put("/melding/spesifikasjonAvBalanse/saldoavskrevetAnleggsmiddel/utgaaendeVerdi" , saldo)

        return saldo
    }

    // Test 1. Flyttes til Unit test senere??
    fun test1() {

        var skattemeldingHM : HashMap<String, Long> = HashMap<String, Long> ()
        var skattemelding = Skattemelding(skattemeldingHM)

        // Testdata input
        skattemelding.data.put("/melding/spesifikasjonAvBalanse/saldoavskrevetAnleggsmiddel/inngaaendeVerdi(1)" , 20000)
        skattemelding.data.put("melding/spesifikasjonAvBalanse/saldoavskrevetAnleggsmiddel/paakostning", -3000)

        // Kalkuler
        beregnAnleggsmidler(skattemelding);

        // Testdata output
        assertEquals (1000, skattemelding.data["/melding/spesifikasjonAvBalanse/saldoavskrevetAnleggsmiddel/utgaaendeVerdi"], "Feil i saldoavskrevetAnleggsmiddel/utgaaendeVerdi")

    }

    fun doc() {
        /*
        1 Kalkyler for anleggsmidler, både avskrivbare og ikke avskrivbare:
            Nr Type Gyldig felt grunnlagForAvskrivningOgIn
            ntektsføring
            åretsAvskrivningOgInntektsførin
            gAvNegativSaldo
            åretsAvskrivningOgInntektsførin
            gAvNegativSaldo
            UtgåendeVerdi
            1 Saldo A
            Kontormaskiner
            o.l.
            inngåendeVerdi
            påkostning
            offentligTilskudd
            justeringAvInngåendeMva
            vederlagVedRealisasjonOgUttak
            vederlagVedRealisasjonOgUttakInntektsførtIÅr
            tilskuddTilInvesteringIDistriktene
            tilbakeføringAvTilskuddTilInvesteringIDistriktene
            utgåendeVerdi
            grunnlagForAvskrivningOgInntektsføring
            åretsAvskrivningOgInntektsføringAvNegativSaldo
            saldogruppe
            avskrivningssats
            nyanskaffelse
            nedskrevetVerdiAvUtskilteDriftsmidler
            erDetFysiskeDriftsmidlerIUtgåendeVerdi
            inngåendeVerdi
            + nyanskaffelse
            + påkostning
            - offentligTilskudd
            +/- justeringAvInngåendeMva
            -
            nedskrevetVerdiAvUtskilteDrifts
            midler
            -
            vederlagVedRealisasjonOgUttak
            - tilbakeføringAvTilskuddTilInves
            teringIDistriktene
            +
            vederlagVedRealisasjonOgUttak
            InntektsførtIÅr
            grunnlagForAvskrivningOgInnte
            ktsføring > 14999:
            grunnlagForAvskrivningOgInntekts
            føring * sats (gyldig verdi 0 -30%,
            default 30%
            grunnlagForAvskrivningOgInnte
            ktsføring mellom 0 og 14999:
            grunnlagForAvskrivningOgInntekts
            føring * sats (gyldig verdi 0 -100%,
            default 100%
            grunnlagForAvskrivningOgInnte
            ktsføring < -14999:
            grunnlagForAvskrivningOgInntekts
            føring * sats (gyldig verdi 30 -
            100%, default 30%
            grunnlagForAvskrivningOgInnte
            ktsføring er mellom -14999 og 0:
            åretsAvskrivningOgInntektsføringA
            vNegativSaldo =
            grunnlagForAvskrivningOgInntekts
            føring
            Ved positivt
            grunnlagForAvskrivningOgInntektsføring :
            grunnlagForAvskrivningOgInntektsføring -
            åretsAvskrivningOgInntektsføringAvNegativSaldo
            Ved negativt
            grunnlagForAvskrivningOgInntektsføring :
            grunnlagForAvskrivningOgInntektsføring +
            åretsAvskrivningOgInntektsføringAvNegativSaldo
            */
    }
}