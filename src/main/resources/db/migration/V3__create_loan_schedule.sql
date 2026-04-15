CREATE TABLE loan_schedule (
                               id BIGSERIAL PRIMARY KEY,
                               payment_number INTEGER NOT NULL,
                               payment_date DATE NOT NULL,
                               total_payment NUMERIC(15,2) NOT NULL,
                               interest_payment NUMERIC(15,2) NOT NULL,
                               principal_payment NUMERIC(15,2) NOT NULL,
                               remaining_balance NUMERIC(15,2) NOT NULL,
                               loan_application_id BIGINT NOT NULL,
                               CONSTRAINT fk_loan_schedule_application
                                   FOREIGN KEY (loan_application_id)
                                       REFERENCES loan_application(id)
);