# BitTorrent Client

A Java-based BitTorrent client that allows users to download files using either a magnet link or a torrent file. The client decodes the data with a custom bencode decoder, connects to trackers to retrieve peer information, performs handshakes with peers, and downloads the file in parts. These parts are then merged to reconstruct the complete file.

## Features

- Support for both magnet links and torrent files
- Custom bencode decoder
- Tracker communication for peer discovery
- Downloads data in chunks and merges them into a single file

## Requirements

- Java 8 or higher

## Installation

1. Clone the repository:
    ```bash
    git clone https://github.com/sachinpandeyatd/BitTorrentClient.git
    ```

2. Navigate to the project directory:
    ```bash
    cd BitTorrentClient
    ```

3. Compile the project (if using Maven or Gradle):
    ```bash
    mvn clean install
    ```

4. Run the BitTorrent client:
    ```bash
    java -jar BitTorrentClient.jar
    ```

## Usage

1. Run the client and provide a magnet link or a torrent file:
    ```bash
    java -jar BitTorrentClient.jar -m "magnet-link"
    ```

    Or if using a torrent file:
    ```bash
    java -jar BitTorrentClient.jar -f "path-to-torrent-file"
    ```

2. The client will decode the torrent file, connect with peers, and start downloading the file in chunks.

