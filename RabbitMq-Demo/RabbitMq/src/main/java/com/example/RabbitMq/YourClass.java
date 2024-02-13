import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class YourClass {

    private static final Map<MessageType, EnrichedTransactionType> TRANSACTION_TYPE_ENUM_MAP = /* Your map initialization here */ null;

    public EnrichedTransactionType getEnrichedTransactionTypeForTransfer(
            MessageType messageType, Accounts accounts, Network network, String sourceSystem, Transaction transaction) {

        ProductAccountType productReceiverAccountType = accounts.getReceiverAccountData()
                .getProductAccountType().orElse(ProductAccountType.UNKNOWN);
        ProductAccountType productSenderAccountType = accounts.getSenderAccountData()
                .getProductAccountType().orElse(ProductAccountType.UNKNOWN);

        return Stream.of(
                checkFDICTransfer(productReceiverAccountType, productSenderAccountType),
                checkThorFDRTransfer(accounts, network, sourceSystem, transaction),
                check0DRODRTransfer(network, sourceSystem),
                TRANSACTION_TYPE_ENUM_MAP.get(messageType)
        )
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst()
                .orElse(EnrichedTransactionType.UNKNOWN);
    }

    private Optional<EnrichedTransactionType> checkFDICTransfer(
            ProductAccountType productReceiverAccountType, ProductAccountType productSenderAccountType) {
        return Optional.of(
                (FDIC.equals(productReceiverAccountType) || FDIC.equals(productSenderAccountType))
                        ? EnrichedTransactionType.FDIC_TRANSFER
                        : null
        );
    }

    private Optional<EnrichedTransactionType> checkThorFDRTransfer(
            Accounts accounts, Network network, String sourceSystem, Transaction transaction) {
        return Optional.of(
                (network.isThor() && PartnerSystem.FDR.name().equals(sourceSystem))
                        ? Stream.of(
                        checkB2BTransfer(accounts),
                        checkCounterPartyPayout(accounts, transaction),
                        EnrichedTransactionType.FDR_PAYMENT)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .findFirst()
                        .orElse(null)
                        : null
        );
    }

    private Optional<EnrichedTransactionType> checkB2BTransfer(Accounts accounts) {
        return Optional.of(
                ("BLOCKCHAIN".equalsIgnoreCase(accounts.getSenderAccountData().getAccount().getDomain()) &&
                        "BLOCKCHAIN".equalsIgnoreCase(accounts.getReceiverAccountData().getAccount().getDomain()))
                        ? EnrichedTransactionType.FDR_B2B_TRANSFER
                        : null
        );
    }

    private Optional<EnrichedTransactionType> checkCounterPartyPayout(Accounts accounts, Transaction transaction) {
        return Optional.of(
                transactionUtils.isCounterPartyPayout(
                        Optional.of(transaction).map(Transaction::getTransactionDetail))
                        ? EnrichedTransactionType.FDR_TCOIN_PAYOUT
                        : null
        );
    }

    private Optional<EnrichedTransactionType> check0DRODRTransfer(Network network, String sourceSystem) {
        return Optional.of(
                (network.is0DR() && PartnerSystem.ODR.name().equals(sourceSystem))
                        ? EnrichedTransactionType.TCOIN_TRANSACT
                        : null
        );
    }
}
