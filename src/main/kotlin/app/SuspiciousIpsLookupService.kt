package app

import dal.ISuspiciousIpsLookup
import dal.ISuspiciousIpsRepository
import logic.IBinaryIpCalculator

class SuspiciousIpsLookupService(private val suspiciousIpsRepository: ISuspiciousIpsRepository,
                                 private val suspiciousLookup: ISuspiciousIpsLookup, private val binaryIpCalculator: IBinaryIpCalculator):ISuspiciousIpsLookupService {
    init {
        populateLookup()
    }

    private fun populateLookup() {
        val suspiciousIpsCidr = suspiciousIpsRepository.getAllCidrSuspiciousIps()

        for (ipCidr in suspiciousIpsCidr) {
            val binaryIp = binaryIpCalculator.convertToBinaryForm(ipCidr.ip)
            val binaryIpMsb = binaryIpCalculator.getIpMsbBits(binaryIp, ipCidr.cidrPrefixLength)
            suspiciousLookup.add(binaryIpMsb)
        }
    }

    override fun isAllowed(incomingIp: String): Boolean {
        val possiblePrefixesLength = suspiciousIpsRepository.getAllCidrPrefixesLengths()

        var isIncomingIpSuspicious = suspiciousLookup.isSuspiciousIp(incomingIp)
        if (isIncomingIpSuspicious){
            return isIncomingIpSuspicious
        }

        var i = 0;
        while (i < possiblePrefixesLength.size && !isIncomingIpSuspicious){
            val possibleIpMsb = getPossibleIpMsb(incomingIp, possiblePrefixesLength, i)
            isIncomingIpSuspicious = suspiciousLookup.isSuspiciousIp(possibleIpMsb)
            i++
        }
        return isIncomingIpSuspicious
    }

    private fun getPossibleIpMsb(
        incomingIp: String,
        possiblePrefixesLength: List<Int>,
        i: Int
    ): String {
        val binaryIp = binaryIpCalculator.convertToBinaryForm(incomingIp)
        val possibleIpMsbLength = possiblePrefixesLength[i]
        return binaryIpCalculator.getIpMsbBits(binaryIp, possibleIpMsbLength)
    }
}