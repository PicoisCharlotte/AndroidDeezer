package interfaces

import com.example.androiddeezer.models.Album

interface AdapterCallbackAlbum {
    fun onClickItem(album: Album)
}