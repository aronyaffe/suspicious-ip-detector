package dal

import model.CidrIp

class SuspiciousIpsRepository(dataBase: IDataBase):ISuspiciousIpsRepository {

    private val cidrIps: MutableList<CidrIp> = mutableListOf()
    private val cidrMuskLengths: MutableList<Int> = mutableListOf()

    init {
        populateLocalCache(dataBase)
    }

    private fun populateLocalCache(dataBase: IDataBase) {
        val suspiciousIps = dataBase.getAllSuspiciousIps()
        for (ip in suspiciousIps) {
            val cidrIpFromIp = ip.split('/').toTypedArray()
            val cidrPrefixLength = cidrIpFromIp[1].toInt()
            val cidrIp = CidrIp(cidrIpFromIp[0], cidrPrefixLength)
            cidrIps.add(cidrIp)
            cidrMuskLengths.add(cidrPrefixLength)
        }
    }

    override fun getAllCidrSuspiciousIps(): List<CidrIp> {
        return cidrIps
    }

    override fun getAllCidrMasksLengths(): List<Int> {
        return cidrMuskLengths
    }
}