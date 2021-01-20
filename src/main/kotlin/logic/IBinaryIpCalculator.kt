package logic

interface IBinaryIpCalculator {
    fun convertFromDecimalToBinary(ipInDecimal: String): String
    fun getIpMsbBits(binaryIp: String, numberOfMsbBits: Int): String
}