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
            val binaryIp = binaryIpCalculator.convertToBinaryForm(ipCidr.ip)
            val binaryIpMsb = binaryIpCalculator.getIpMsbBits(binaryIp, ipCidr.cidrMaskLength)
            suspiciousLookup.add(binaryIpMsb)
        }
    }

    override fun isAllowed(incomingIp: String): Boolean {
        val possiblePrefixesLength = suspiciousIpsRepository.getAllCidrMasksLengths()

        var isSuspiciousIp = suspiciousLookup.isSuspiciousIp(incomingIp)
        if (isSuspiciousIp){
            return false
        }

        var i = 0
        while (i < possiblePrefixesLength.size && !isSuspiciousIp){
            val possibleIpMsb = getPossibleIpMsb(incomingIp, possiblePrefixesLength, i)
            isSuspiciousIp = suspiciousLookup.isSuspiciousIp(possibleIpMsb)
            i++
        }
        return !isSuspiciousIp
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