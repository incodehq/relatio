ALTER VIEW [api].[AspectV1] AS

WITH a AS (
SELECT
	id,
	collectedAt,
	eventId,
	profileId,
	[type],
	[value]
FROM
	(
	SELECT
		id,
		collectedAt,
		eventId,
		profileId,
		[type],
		[value],
		ROW_NUMBER() OVER(PARTITION by profileID, [type] ORDER BY collectedAt DESC) AS rn
	FROM
		Aspect WHERE NOT profileId IS NULL
	) x
WHERE rn = 1
)

SELECT
	a.profileId,
	a.type AS aspectType,
	a.value AS aspectValue,
	a.collectedAt as aspectCollectedAt,
	es.createdAt eventSourceCreatedAt,
	es.type AS eventSourceType,
	es.name AS eventSourceName
FROM
	a
	INNER JOIN dbo.[Event] e ON e.id = a.eventId
	INNER JOIN dbo.[EventSource] es ON es.id = e.eventSourceId

GO

CREATE view [api].[AspectPivotedV1] as SELECT profileId, eventSourceType, [InfopointRating],[Gender],[RelaxAreaRating],[CarOwner],[PlaygroundUser],[OnlineShopper],[DateCollected],[EmailAccount],[ThirdPartyConsent],[CellPhoneNumber],[CentreRestroomsRating],[ProfilingConsent],[AgeGroup],[WifiUser],[MarketingConsent],[Address],[FirstName],[PrivacyConsent],[WifiRating],[PostalCode],[YearOfBirth],[NurseryUser],[Parent],[City],[ApproximateDateOfBirth],[RelaxAreaUser],[InfopointUser],[PlaygroundRating],[GeneralPhoneNumber],[LastName],[TransportUsed],[DogOwner],[DateOfBirth],[CarwashUser],[FamilySize],[NurseryRating],[CentreRestroomsUsed],[CarwashRating],[GameType],[GamePlayDateAndTime] from
            (
                select
					profileId
					, eventSourceType
                    , aspectValue
                    , aspectType
                from api.AspectV1
           ) x
            pivot
            (
                 max(aspectValue)
                for aspectType in ([InfopointRating],[Gender],[RelaxAreaRating],[CarOwner],[PlaygroundUser],[OnlineShopper],[DateCollected],[EmailAccount],[ThirdPartyConsent],[CellPhoneNumber],[CentreRestroomsRating],[ProfilingConsent],[AgeGroup],[WifiUser],[MarketingConsent],[Address],[FirstName],[PrivacyConsent],[WifiRating],[PostalCode],[YearOfBirth],[NurseryUser],[Parent],[City],[ApproximateDateOfBirth],[RelaxAreaUser],[InfopointUser],[PlaygroundRating],[GeneralPhoneNumber],[LastName],[TransportUsed],[DogOwner],[DateOfBirth],[CarwashUser],[FamilySize],[NurseryRating],[CentreRestroomsUsed],[CarwashRating],[GameType],[GamePlayDateAndTime])
            ) p
GO



