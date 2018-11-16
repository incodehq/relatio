UPDATE a SET [type] = 'MarketingConsent' FROM Aspect a WHERE [type] = 'HasGivenMarketingConsent'
UPDATE a SET [type] = 'PrivacyConsent' FROM Aspect a WHERE [type] = 'HasReadPrivacyPolicy'
UPDATE a SET [type] = 'PostalCode' FROM Aspect a WHERE [type] = 'PostCode'
GO

ALTER TABLE [dbo].[Profile]
   ADD [privacyConsent] [VARCHAR](255) NULL
GO

EXEC sp_rename 'Profile.hasGivenMarketingConsent', 'marketingConsent', 'COLUMN';
