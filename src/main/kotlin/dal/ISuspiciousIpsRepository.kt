package dal

import model.CidrIp

interface ISuspiciousIpsRepository {
    fun getAllCidrSuspiciousIps(): List<CidrIp>
    fun getAllCidrMasksLengths(): List<Int>
}