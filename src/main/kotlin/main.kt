import app.ISuspiciousIpsLookupService
import app.SuspiciousIpsLookupService
import dal.*
import logic.BinaryIpCalculator
import logic.IBinaryIpCalculator

fun main(args: Array<String>) {

    val suspiciousIpsLookupService: ISuspiciousIpsLookupService = bootstrapService()

    var res = suspiciousIpsLookupService.isAllowed("12.13.15.63")
    res = suspiciousIpsLookupService.isAllowed("12.13.15.64")
    res = suspiciousIpsLookupService.isAllowed("12.13.15.60")

}

private fun bootstrapService(): ISuspiciousIpsLookupService {
    val db: IDataBase = DataBase();
    val suspiciousIpsRepository: ISuspiciousIpsRepository = SuspiciousIpsRepository(db)
    val suspiciousIpsLookup: ISuspiciousIpsLookup = SuspiciousIpsLookupTable()
    val binCalculator: IBinaryIpCalculator = BinaryIpCalculator()
    val suspiciousIpsLookupService: ISuspiciousIpsLookupService =
        SuspiciousIpsLookupService(suspiciousIpsRepository, suspiciousIpsLookup, binCalculator)
    return suspiciousIpsLookupService
}