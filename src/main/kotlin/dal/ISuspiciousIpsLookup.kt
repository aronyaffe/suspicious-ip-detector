package dal

interface ISuspiciousIpsLookup {
    fun add(suspiciousIp: String)
    fun isSuspiciousIp(suspiciousIpToCheck: String): Boolean
}