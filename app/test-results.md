# Weather App Test Results

## Valid City Tests

| City | Result |
|---|---|
| Colombo | Weather data displayed successfully |
| Kandy | Weather data displayed successfully |
| London | Weather data displayed successfully |

## Invalid City Test

**Input:** asdfghjkl

**Expected:** City not found. Please check the spelling.

**Result:** Passed

## Empty Input Test

**Input:** Empty

**Expected:** Please enter a city name

**Result:** Passed

## Network Error Test

**Condition:** Internet connection disabled

**Expected:** Network error. Check your connection and try again.

**Result:** Passed

## Screenshots

- App interface in idle state
- Successful weather result
- Error-handling case