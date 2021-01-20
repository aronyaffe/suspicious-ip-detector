package dal

import model.CidrIp

interface ISuspiciousIpsRepository {
    fun getAllCidrSuspiciousIps(): List<CidrIp>
    fun getAllCidrPrefixesLengths(): List<Int>
}