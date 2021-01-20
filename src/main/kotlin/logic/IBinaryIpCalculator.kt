package logic

interface IBinaryIpCalculator {
    fun convertToBinaryForm(ip: String): String
    fun getIpMsbBits(binaryIp: String, numberOfMsbBits: Int): String
}