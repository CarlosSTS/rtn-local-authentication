# `rtn-local-authentication`

`rtn-local-authentication` is a local authentication library for React Native, built using Turbo Modules. It provides an interface for biometric and PIN authentication on Android devices.

## Features

- Support for fingerprint, PIN, and pattern authentication on Android.
- Fully compatible with React Native's Turbo Module system.
- Simple API to integrate local authentication into your React Native application.

## Installation

### Prerequisites

Ensure your React Native project is properly configured to use Turbo Modules. For more details, follow the official [React Native Turbo Modules documentation](https://reactnative.dev/docs/the-new-architecture/landing-page).

> If your version of react-native does not support turbo-modules, I recommend https://github.com/tradle/react-native-local-auth

### Install the package

```bash
npm install @carlossts/rtn-local-authentication
or
yarn add @carlossts/rtn-local-authentication
```

## UI

### authenticate method

![fingerprintOrPin](https://firebasestorage.googleapis.com/v0/b/portfolio-web-7fbff.appspot.com/o/libs_npm%2Fcarlossts-rtn-local-authentication%2Fimage01.png?alt=media&token=293510fd-170a-42bc-b1e9-d9bd06a888ec)
![PIN](https://firebasestorage.googleapis.com/v0/b/portfolio-web-7fbff.appspot.com/o/libs_npm%2Fcarlossts-rtn-local-authentication%2Fimage02.png?alt=media&token=81209a98-48e0-4ebe-830b-3c1aa1a54d8f)
![fingerprintOrPattern](https://firebasestorage.googleapis.com/v0/b/portfolio-web-7fbff.appspot.com/o/libs_npm%2Fcarlossts-rtn-local-authentication%2Fimage03.png?alt=media&token=e27c55c1-c3f4-44b7-99b7-f5dd2e069425)
![pattern](https://firebasestorage.googleapis.com/v0/b/portfolio-web-7fbff.appspot.com/o/libs_npm%2Fcarlossts-rtn-local-authentication%2Fimage04.png?alt=media&token=712ac8eb-8c3e-44a5-9f47-57297bffb685)

### isDeviceSecure method
![isDeviceSecure](https://firebasestorage.googleapis.com/v0/b/portfolio-web-7fbff.appspot.com/o/libs_npm%2Fcarlossts-rtn-local-authentication%2Fimage05.png?alt=media&token=2f76b088-23c5-4f14-b248-649f3065299d)

## API Reference

## Methods

### `authenticate(map: { reason?: string; description?: string }): Promise<string>()`

Local device authentication process (using password, PIN, pattern or fingerprint), verifying that the device is protected by some security method, such as a password or biometrics.

Observation: If the device does not have local authentication, return success with the code WITHOUT_AUTHENTICATION.

## Options

| Option                | Description                                       |
| --------------------- | ------------------------------------------------- |
| reason                | Action title                                      |
| description           | Action description                                |


## Usage

```js
import React, { useCallback, useEffect } from 'react';
import { Alert, View } from 'react-native';
import {RNTLocalAuthentication} from 'rtn-local-authentication';

const App = () => {
  const authenticationLocal = useCallback(async () => {
    try {
      await RNTLocalAuthentication.authenticate({
        reason: 'Please authenticate yourself',
        description: 'Enter your password or fingerprint',
      });
    } catch (error) {
      Alert.alert('Authentication Failed', error.message);
    }
  }, []);

  useEffect(() => {
    authenticationLocal();
  }, [authenticationLocal]);

  return <View />;
};

export default App;
```

## ErrorCode

| Code                  | Description                                       |
| --------------------- | ------------------------------------------------- |
| E_AUTH_CANCELLED      | User canceled the authentication                  |
| E_ONE_REQ_AT_A_TIME   | Authentication already in progress                |
| E_FAILED_TO_SHOW_AUTH | Failed to create authentication intent            |

##

### `isDeviceSecure(): Promise<boolean>`

Checks if the device has some type of authentication.

## Usage

```js
import React, { useCallback, useEffect } from 'react';
import { Alert, View } from 'react-native';
import {RNTLocalAuthentication} from 'rtn-local-authentication';

const App = () => {
  const isDeviceSecure = useCallback(async () => {
    try {
     const result = await RNTLocalAuthentication?.isDeviceSecure();
    Alert.alert('Is it a secure device ?', result ? 'Yes' : 'No');
    } catch (error) {
      Alert.alert('isDeviceSecure Failed', error.message);
    }
  }, []);

  useEffect(() => {
    isDeviceSecure();
  }, [isDeviceSecure]);

  return <View />;
};

export default App;
```

## License

[MIT](LICENSE.md)

