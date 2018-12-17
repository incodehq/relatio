EXEC sp_RENAME '[dbo].[Profile].birthdate' , 'dateOfBirth', 'COLUMN'

ALTER TABLE [dbo].[Profile]
ADD approximateDateOfBirth date
