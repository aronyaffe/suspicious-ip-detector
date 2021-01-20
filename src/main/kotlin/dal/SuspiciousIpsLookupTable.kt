package dal

class SuspiciousIpsLookupTable: ISuspiciousIpsLookup {
    var suspiciousIpsLookupTable: MutableSet<String> = mutableSetOf()

    override fun add(suspiciousIp: String) {
        suspiciousIpsLookupTable.add(suspiciousIp)
    }

    override fun isSuspiciousIp(suspiciousIpToCheck: String): Boolean {
        return suspiciousIpsLookupTable.contains(suspiciousIpToCheck)
    }
}