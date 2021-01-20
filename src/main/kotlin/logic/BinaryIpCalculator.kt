package logic

class BinaryIpCalculator: IBinaryIpCalculator {
    override fun convertToBinaryForm(ip: String): String {
        val ipOctetsInDecimal = ip.split('.')
        var binaryIp = StringBuilder()

        for (decimalOctet in ipOctetsInDecimal){
            binaryIp.append(decimalOctet.toInt().toBinary(8))
        }
        return binaryIp.toString()
    }

    override fun getIpMsbBits(binaryIp: String, numberOfMsbBits: Int): String {
        val numberOfLsbZeros = binaryIp.length - numberOfMsbBits
        val lsbSuffixZeros = "0".repeat(numberOfLsbZeros)
        val ipMsbBits = binaryIp.take(numberOfMsbBits)
        return "${ipMsbBits + lsbSuffixZeros}"
    }

    private fun Int.toBinary(len: Int): String {
        return String.format("%" + len + "s", this.toString(2)).replace(" ".toRegex(), "0")
    }
}
