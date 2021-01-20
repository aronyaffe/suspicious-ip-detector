import app.ISuspiciousIpsLookupService
import app.SuspiciousIpsLookupService
import dal.*
import logic.BinaryIpCalculator
import logic.IBinaryIpCalculator

fun main() {

    val suspiciousIpsLookupService: ISuspiciousIpsLookupService = bootstrapService()

    var res = suspiciousIpsLookupService.isAllowed("12.13.15.63")
    //res = suspiciousIpsLookupService.isAllowed("12.13.15.64") //should be false
    //res = suspiciousIpsLookupService.isAllowed("12.13.15.60") //should be true
}

private fun bootstrapService(): ISuspiciousIpsLookupService {
    val db: IDataBase = DataBase()
    val suspiciousIpsRepository: ISuspiciousIpsRepository = SuspiciousIpsRepository(db)
    val suspiciousIpsLookup: ISuspiciousIpsLookup = SuspiciousIpsLookupTable()
    val binCalculator: IBinaryIpCalculator = BinaryIpCalculator()

    return SuspiciousIpsLookupService(suspiciousIpsRepository, suspiciousIpsLookup, binCalculator)
}