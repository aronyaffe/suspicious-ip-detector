package logic

class BinaryIpCalculator: IBinaryIpCalculator {
    val BINARY_RADIX = 2
    val OCTET_BITS_NUMBER = 8

    override fun convertFromDecimalToBinary(ipInDecimal: String): String {
        val ipOctetsInDecimal = ipInDecimal.split('.')
        val binaryIp = StringBuilder()

        for (decimalOctet in ipOctetsInDecimal){
            binaryIp.append(decimalOctet.toInt().toBinary(OCTET_BITS_NUMBER))
        }
        return binaryIp.toString()
    }

    override fun getIpMsbBits(binaryIp: String, numberOfMsbBits: Int): String {
        val numberOfLsbZeros = binaryIp.length - numberOfMsbBits
        val lsbSuffixZeros = "0".repeat(numberOfLsbZeros)
        val ipMsbBits = binaryIp.take(numberOfMsbBits)
        return ipMsbBits + lsbSuffixZeros
    }

    private fun Int.toBinary(len: Int): String {
        return String.format("%" + len + "s", this.toString(BINARY_RADIX)).replace(" ".toRegex(), "0")
    }
}
