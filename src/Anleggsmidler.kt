class Anleggsmidler {

    // mapping av felter fra XML/XSD input
    var saldoavskrevetAnleggsmiddelObjektidentifikator: Int = 1
    var saldoavskrevetAnleggsmiddelInngaaendeVerdi: Long = -999
    var saldoavskrevetAnleggsmiddelPaakostning: Long = -999

    // kalkulerte informasjonsfelt - output
    var grunnlagForAvskrivningOgInntektsforing: Long = -999
    var aaretsAvskrivningOgInntektsforingAvNegativSaldo: Long = -999

    fun mapIndata(skattemeldingHM: HashMap<String, Long>): Int {

        saldoavskrevetAnleggsmiddelInngaaendeVerdi =
            skattemeldingHM.get("/melding/spesifikasjonAvBalanse/saldoavskrevetAnleggsmiddel/inngaaendeVerdi")!!
        saldoavskrevetAnleggsmiddelPaakostning =
            skattemeldingHM.get("melding/spesifikasjonAvBalanse/saldoavskrevetAnleggsmiddel/paakostning")!!

        return 1
    }

    fun beregnAnleggsmidler(skattemeldingHM: HashMap<String, Long>): Long {
        var saldo: Long


        // kalkulerte satser og grensesverdier
        val GRENSEVERDI: Long = 14999L
        var sats1: Long = 30 // (gyldig verdi 0 -30%, default 30%
        var sats2: Long = 70
        var sats3: Long = 100
        var sats4: Long = 35
        var sats: Long = 0

        // doc Ref kap 1.1 Kalkyler for anleggsmidler, både avskrivbare og ikke avskrivbare:

        mapIndata(skattemeldingHM)

        // beregn grunnlagForAvskrivningOgInntektsforing
        grunnlagForAvskrivningOgInntektsforing =
            saldoavskrevetAnleggsmiddelInngaaendeVerdi + saldoavskrevetAnleggsmiddelPaakostning

        // bestem sats ut fra grunnlagForAvskrivningOgInntektsforing
        if (grunnlagForAvskrivningOgInntektsforing > GRENSEVERDI)
            sats = sats1
        if ((grunnlagForAvskrivningOgInntektsforing < GRENSEVERDI) and (grunnlagForAvskrivningOgInntektsforing >= 0))
            sats = sats2
        if ((grunnlagForAvskrivningOgInntektsforing < 0) and (grunnlagForAvskrivningOgInntektsforing > -GRENSEVERDI))
            sats = sats3
        if (grunnlagForAvskrivningOgInntektsforing < 0 - GRENSEVERDI)
            sats = sats4

        aaretsAvskrivningOgInntektsforingAvNegativSaldo = grunnlagForAvskrivningOgInntektsforing * sats / 100
        saldo = grunnlagForAvskrivningOgInntektsforing - aaretsAvskrivningOgInntektsforingAvNegativSaldo

        return saldo
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