# Implement Error Handling and Update UI Logic in MainActivity

The goal is to complete "Task 08" by implementing error handling in the weather API response and integrating the display logic provided for successful responses.

## Proposed Changes

### [app](file:///C:/Users/USER/Desktop/MyGitProjects/-weather-app-android/app)

#### [MODIFY] [MainActivity.kt](file:///C:/Users/USER/Desktop/MyGitProjects/-weather-app-android/app/src/main/java/com/example/weatherapp/MainActivity.kt)
- Initialize all required `TextView`s (`tvCity`, `tvTemp`, `tvCondition`, `tvHumidity`, `tvWind`) from the layout.
- Update the `onResponse` callback to include:
    - The success logic provided by the user (displaying weather data).
    - Robust error handling in the `else` block to display appropriate messages in `tvStatus` for different HTTP error codes (e.g., 404 for city not found, 401 for invalid API key).
- Ensure `tvStatus` visibility is toggled correctly (visible on error, hidden on success).

## Verification Plan

### Manual Verification
1. **Successful Search**: Enter a valid city name (e.g., "London") and verify weather details are displayed.
2. **City Not Found**: Enter an invalid city name and verify `tvStatus` shows "City not found (404)".
3. **Invalid API Key**: Use an invalid API key and verify `tvStatus` shows an appropriate error message (if applicable).
4. **Network Failure**: Disable internet and verify `tvStatus` shows "Network failure".
