package app

interface ISuspiciousIpsLookupService {
    fun isAllowed(incomingIp: String): Boolean
}