CREATE OR REPLACE FUNCTION transfer_money(
    in_amount DOUBLE PRECISION,
    in_account_number INTEGER,
    in_destinataire_account_number INTEGER
) RETURNS VOID AS $$
BEGIN
    UPDATE account
    SET main_balance = main_balance - in_amount
    WHERE account_number = in_account_number;

    UPDATE account
    SET main_balance = main_balance + in_amount
    WHERE account_number = in_destinataire_account_number;

    INSERT INTO transfer (amount, reason, state, account_number, destinataire_account_number)
    VALUES (in_amount, 'Transfert d\'argent', 'Réussi', in_account_number, in_destinataire_account_number);

    COMMIT;
END;
$$ LANGUAGE plpgsql;
