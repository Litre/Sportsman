

Scenario: 1 Adding new wrestler
Meta:
@functional
Given user opens main page
When user inputs auto and test on main page
When add new sportsman with credentials:
|LastName|BirthDate |Region   |FST|Style|Year|FirstName|MiddleName|Age   |
|Klytchko|06-01-1945|Zaporizka|SK |FW   |2013|Vitaliy  |Name      |Senior|
And save changes
Then check that sportsman was added
When update Klytchko region to Donetska
Then check that Klytchko has region Donetska
When delete sportsman
Then check that Klytchko doesn't exist


Scenario: 2 Filtering of sportsmen
Given user opens main page
When user inputs auto and test on main page
And searches sportsman in Volynska region and for 2014 year
Then check proper displaying:
|rowNumber|valueToCheck|
|3        |Volynska    |
|5        |2014        |

Scenario: 3 Upload sportsmen photo
Given user opens main page
When user inputs auto and test on main page
When add new sportsman with credentials:
|LastName|BirthDate |Region|FST|Style|Year|FirstName|MiddleName|Age   |
|Tommy   |06-01-1988|Sumska|SK |FW   |2017|Black    |John      |Senior|
And save changes
Then check that sportsman was added
When upload photo
Then check that right photo was uploaded
When delete sportsman


Scenario: 4 Upload attachment
Given user opens main page
When user inputs auto and test on main page
When add new sportsman with credentials:
|LastName|BirthDate |Region|FST|Style|Year|FirstName|MiddleName|Age   |
|Greg    |06-01-1988|Sumska|SK |FW   |2016|Black    |John      |Senior|
And save changes
Then check that sportsman was added
When upload attachment
Then check that right attachment was uploaded
When delete sportsman

