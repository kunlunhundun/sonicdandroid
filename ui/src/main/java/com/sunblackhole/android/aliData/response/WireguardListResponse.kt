/*
 * Copyright © 2020 WireGuard LLC. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

package com.sunblackhole.android.aliData.response

class WireguardListResponse : BaseResponseObject() {

    val data = Body()

    class Body {
        var wireguardList: MutableList<VpnServiceObject> = mutableListOf();
    }

    data class VpnServiceObject (
        var lineName: String? = null,
        var wireguards: MutableList<VpnObject>? = null

    )

    data class VpnObject (
            var id: String? = null,
            var lineName: String? = null,
            var serviceId: String? = null,
            var privatekey: String? = null,
            var address: String? = null,
            var dns: String? = null,
            var mtu: String? = null,
            var publickey: String? = null,
            var endpoint: String? = null,
            var allowedIps: String? = null,
            var persistentKeepalive: String? = null
    )

}