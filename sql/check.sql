ALTER TABLE Subscription ADD CONSTRAINT con CHECK (subscription_type >=0 AND subscription_type <=2)
