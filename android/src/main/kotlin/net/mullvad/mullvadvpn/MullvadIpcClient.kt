package net.mullvad.mullvadvpn

import java.io.File
import java.io.InputStream
import java.io.OutputStream

import net.mullvad.mullvadvpn.model.AccountData
import net.mullvad.mullvadvpn.model.RelayList
import net.mullvad.mullvadvpn.model.RelaySettingsUpdate
import net.mullvad.mullvadvpn.model.Settings
import net.mullvad.mullvadvpn.model.TunnelStateTransition

class MullvadIpcClient {
    init {
        System.loadLibrary("mullvad_jni")
        initialize()
    }

    var onTunnelStateChange: ((TunnelStateTransition) -> Unit)? = null

    external fun connect()
    external fun disconnect()
    external fun getAccountData(accountToken: String): AccountData?
    external fun getCurrentVersion(): String
    external fun getRelayLocations(): RelayList
    external fun getSettings(): Settings
    external fun setAccount(accountToken: String?)
    external fun updateRelaySettings(update: RelaySettingsUpdate)

    private external fun initialize()

    private fun notifyTunnelStateEvent(event: TunnelStateTransition) {
        onTunnelStateChange?.invoke(event)
    }
}
