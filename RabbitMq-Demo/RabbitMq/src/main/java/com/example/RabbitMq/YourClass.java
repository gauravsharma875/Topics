import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class YourClass {

    private final Map<MessageType, EnrichedTransactionType> TRANSACTION_TYPE_ENUM_MAP = /* Your map initialization here */ null;

    public EnrichedTransactionType getEnrichedTransactionTypeForTransfer(
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
                .flatMap(Function.identity())
                .findFirst()
                .orElse(TRANSACTION_TYPE_ENUM_MAP.getOrDefault(messageType, EnrichedTransactionType.UNKNOWN));
    }

    private Stream<Optional<EnrichedTransactionType>> checkFDICTransfer(
            ProductAccountType productReceiverAccountType, ProductAccountType productSenderAccountType) {
        return Stream.of(
                Optional.ofNullable(FDIC.equals(productReceiverAccountType) || FDIC.equals(productSenderAccountType)
                        ? EnrichedTransactionType.FDIC_TRANSFER
                        : null)
        );
    }

    private Stream<Optional<EnrichedTransactionType>> checkThorFDRTransfer(
            Accounts accounts, Network network, String sourceSystem, Transaction transaction) {
        return Stream.of(
                checkB2BTransfer(accounts),
                checkCounterPartyPayout(accounts, transaction),
                Optional.ofNullable((network.isThor() && PartnerSystem.FDR.name().equals(sourceSystem))
                        ? EnrichedTransactionType.FDR_PAYMENT
                        : null)
        );
    }

    private Optional<EnrichedTransactionType> checkB2BTransfer(Accounts accounts) {
        return Optional.of(
                Optional.ofNullable(("BLOCKCHAIN".equalsIgnoreCase(accounts.getSenderAccountData().getAccount().getDomain()) &&
                        "BLOCKCHAIN".equalsIgnoreCase(accounts.getReceiverAccountData().getAccount().getDomain()))
                        ? EnrichedTransactionType.FDR_B2B_TRANSFER
                        : null)
        );
    }

    private Optional<EnrichedTransactionType> checkCounterPartyPayout(Accounts accounts, Transaction transaction) {
        return Optional.of(
                Optional.ofNullable(transactionUtils.isCounterPartyPayout(
                        Optional.of(transaction).map(Transaction::getTransactionDetail))
                        ? EnrichedTransactionType.FDR_TCOIN_PAYOUT
                        : null)
        );
    }

    private Stream<Optional<EnrichedTransactionType>> check0DRODRTransfer(Network network, String sourceSystem) {
        return Stream.of(
                Optional.ofNullable((network.is0DR() && PartnerSystem.ODR.name().equals(sourceSystem))
                        ? EnrichedTransactionType.TCOIN_TRANSACT
                        : null)
        );
    }
}
