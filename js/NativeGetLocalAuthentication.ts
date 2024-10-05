import type { TurboModule } from "react-native/Libraries/TurboModule/RCTExport";
import { TurboModuleRegistry } from "react-native";

export interface Spec extends TurboModule {
  isDeviceSecure(): Promise<boolean>;
  authenticate(map: { reason?: string; description?: string }): Promise<string>;
}

export default TurboModuleRegistry.get<Spec>("RTNLocalAuthentication") as Spec | null;
