package dal

import model.CidrIp

class SuspiciousIpsRepository(private val dataBase: IDataBase):ISuspiciousIpsRepository {

    private val cidrIps: MutableList<CidrIp> = mutableListOf()
    private val cidrPrefixesLengths: MutableList<Int> = mutableListOf()

    init {
        val suspiciousIps = dataBase.getAllSuspiciousIps()
        for (ip in suspiciousIps){
            var cidrIpFromIp = ip.split('/') .toTypedArray()
            val cidrPrefixLength = cidrIpFromIp[1].toInt()
            var cidrIp = CidrIp(cidrIpFromIp[0], cidrPrefixLength)
            cidrIps.add(cidrIp)
            cidrPrefixesLengths.add(cidrPrefixLength)
        }
    }
    override fun getAllCidrSuspiciousIps(): List<CidrIp> {
        return cidrIps
    }

    override fun getAllCidrPrefixesLengths(): List<Int> {
        return cidrPrefixesLengths
    }
}