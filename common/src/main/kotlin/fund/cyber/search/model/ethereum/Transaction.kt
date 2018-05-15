package fund.cyber.search.model.ethereum

import fund.cyber.search.model.PoolItem
import fund.cyber.search.model.chains.ChainEntity
import java.math.BigDecimal
import java.time.Instant

val weiToEthRate = BigDecimal("1E-18")

data class EthereumTx(
        val hash: String,
        val nonce: Long,                //parsed from hex
        val blockHash: String?,        //null when its pending
        val blockNumber: Long,         //parsed from hex   //null when its pending
        val firstSeenTime: Instant,
        val blockTime: Instant?,
        val positionInBlock: Int,       //txes from one block ordering field
        val from: String,
        val to: String?,                //null when its a contract creation transaction.
        val value: BigDecimal,          //decimal   //parsed from hex
        val gasPrice: BigDecimal,      //parsed from hex
        val gasLimit: Long,            //parsed from hex
        val gasUsed: Long,             //parsed from hex
        val fee: BigDecimal,            //decimal //calculated
        val input: String,
        val createdSmartContract: String?            //creates contract hash
) : PoolItem, ChainEntity {
    fun contractsUsedInTransaction() = listOfNotNull(from, to, createdSmartContract)
        .filter { contract -> contract.isNotEmpty() }

    fun mempoolState() = EthereumTx(
        hash = this.hash, nonce = this.nonce, blockHash = null, blockNumber = -1,
        blockTime = null, positionInBlock = -1, from = this.from, to = this.to,
        value = this.value, gasPrice = this.gasPrice, gasLimit = this.gasLimit,
        gasUsed = 0, fee = this.gasPrice * this.gasLimit.toBigDecimal(), input = this.input,
        createdSmartContract = this.createdSmartContract, firstSeenTime = this.firstSeenTime
    )
}
