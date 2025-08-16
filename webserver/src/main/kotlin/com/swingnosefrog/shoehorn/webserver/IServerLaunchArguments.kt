package com.swingnosefrog.shoehorn.webserver


interface IServerLaunchArguments {

    val port: Int
    val virtualHosts: List<String>

}