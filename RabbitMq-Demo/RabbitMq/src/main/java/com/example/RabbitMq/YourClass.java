import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class YourClass {

    private static final Map<MessageType, EnrichedTransactionType> TRANSACTION_TYPE_ENUM_MAP = /* Your map initialization here */ null;

    private EnrichedTransactionType getEnrichedTransactionTypeForTransfer(
            MessageType messageType, Accounts accounts, Network network, String sourceSystem, Transaction transaction) {

        ProductAccountType productReceiverAccountType = accounts.getReceiverAccountData()
                .getProductAccountType().orElse(ProductAccountType.UNKNOWN);
        ProductAccountType productSenderAccountType = accounts.getSenderAccountData()
                .getProductAccountType().orElse(ProductAccountType.UNKNOWN);

        return Stream.of(
                checkFDICTransfer(productReceiverAccountType, productSenderAccountType),
                checkThorFDRTransfer(accounts, network, sourceSystem, transaction),
                check0DRODRTransfer(network, sourceSystem)
        )
                .flatMap(Optional::stream)
                .findFirst()
                .orElse(TRANSACTION_TYPE_ENUM_MAP.getOrDefault(messageType, EnrichedTransactionType.UNKNOWN));
    }

    private Optional<EnrichedTransactionType> checkFDICTransfer(
            ProductAccountType productReceiverAccountType, ProductAccountType productSenderAccountType) {
        return (FDIC.equals(productReceiverAccountType) || FDIC.equals(productSenderAccountType))
                ? Optional.of(EnrichedTransactionType.FDIC_TRANSFER)
                : Optional.empty();
    }

    private Stream<EnrichedTransactionType> checkThorFDRTransfer(
            Accounts accounts, Network network, String sourceSystem, Transaction transaction) {
        return (network.isThor() && PartnerSystem.FDR.name().equals(sourceSystem))
                ? Stream.of(
                checkB2BTransfer(accounts),
                checkCounterPartyPayout(accounts, transaction),
                EnrichedTransactionType.FDR_PAYMENT)
                : Stream.empty();
    }

    private Optional<EnrichedTransactionType> checkB2BTransfer(Accounts accounts) {
        return ("BLOCKCHAIN".equalsIgnoreCase(accounts.getSenderAccountData().getAccount().getDomain()) &&
                "BLOCKCHAIN".equalsIgnoreCase(accounts.getReceiverAccountData().getAccount().getDomain()))
                ? Optional.of(EnrichedTransactionType.FDR_B2B_TRANSFER)
                : Optional.empty();
    }

    private Optional<EnrichedTransactionType> checkCounterPartyPayout(Accounts accounts, Transaction transaction) {
        return transactionUtils.isCounterPartyPayout(
                Optional.of(transaction).map(Transaction::getTransactionDetail))
                ? Optional.of(EnrichedTransactionType.FDR_TCOIN_PAYOUT)
                : Optional.empty();
    }

    private Stream<EnrichedTransactionType> check0DRODRTransfer(Network network, String sourceSystem) {
        return (network.is0DR() && PartnerSystem.ODR.name().equals(sourceSystem))
                ? Stream.of(EnrichedTransactionType.TCOIN_TRANSACT)
                : Stream.empty();
    }
}
