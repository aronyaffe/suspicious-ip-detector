package app

import dal.ISuspiciousIpsLookup
import dal.ISuspiciousIpsRepository
import logic.IBinaryIpCalculator

class SuspiciousIpsLookupService(private val suspiciousIpsRepository: ISuspiciousIpsRepository,
                                 private val suspiciousLookup: ISuspiciousIpsLookup,
                                 private val binaryIpCalculator: IBinaryIpCalculator)
    :ISuspiciousIpsLookupService {

    init {
        populateLookup()
    }

    private fun populateLookup() {
        val suspiciousIpsCidr = suspiciousIpsRepository.getAllCidrSuspiciousIps()

        for (ipCidr in suspiciousIpsCidr) {
            val binaryIp = binaryIpCalculator.convertFromDecimalToBinary(ipCidr.ip)
            val binaryIpMsb = binaryIpCalculator.getIpMsbBits(binaryIp, ipCidr.cidrMaskLength)
            suspiciousLookup.add(binaryIpMsb)
        }
    }

    override fun isAllowed(incomingIp: String): Boolean {
        val allCidrMasksLength = suspiciousIpsRepository.getAllCidrMasksLengths()

        var isSuspiciousIp = suspiciousLookup.isSuspiciousIp(incomingIp)
        if (isSuspiciousIp){
            return false
        }

        var i = 0
        while (i < allCidrMasksLength.size && !isSuspiciousIp){
            val maskedIp = getMaskedIp(incomingIp, allCidrMasksLength[i])
            isSuspiciousIp = suspiciousLookup.isSuspiciousIp(maskedIp)
            i++
        }
        return !isSuspiciousIp
    }

    private fun getMaskedIp(
        incomingIp: String,
        possibleIpMsbLength: Int
    ): String {
        val binaryIp = binaryIpCalculator.convertFromDecimalToBinary(incomingIp)
        return binaryIpCalculator.getIpMsbBits(binaryIp, possibleIpMsbLength)
    }
}