fun main() {
    println("Starter kalkyle App")

    // Testdata
    var skattemelding : HashMap<String, Long> = HashMap<String, Long> ()
    skattemelding.put("/melding/spesifikasjonAvBalanse/saldoavskrevetAnleggsmiddel/inngaaendeVerdi" , 20000)
    skattemelding.put("melding/spesifikasjonAvBalanse/saldoavskrevetAnleggsmiddel/paakostning", -3000)

    var anleggsmidler: Anleggsmidler = Anleggsmidler()
    var balanse: Balanse = Balanse()

    var x3: Long = + anleggsmidler.beregnAnleggsmidler(skattemelding) + balanse.beregnBalanse(skattemelding)
    println("Sum: " + x3)

}







