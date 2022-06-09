# Heroku Deployed Docker Containers

## Author
Alonzo Roman

## Links
- [Music Store Catalog] (https://enigmatic-refuge-39191.herokuapp.com/)
- [Music Store Recommendations] (https://tranquil-brushlands-66152.herokuapp.com/)

# Instructions

Catalog Endpoinds to make all CRUD requests:
(Replace 1 with relevant ID number)

# Music Store Catalog

## Label

- CREATE (POST) a label: /label
```
JSON Request Body

{
    "name": "New Label Name",
    "website": "https://www.newlabelname.com"
}
```
- GET all labels: /label
- GET one label: /label/1
- UPDATE (PUT) one label: /label/1 
```
JSON Request Body

{
    "id": 1,
    "name": "Label Name",
    "website": "https://www.labelname.com"
}
```
- DELETE one label: /label/1

## Artist

- CREATE (POST) an artist: /artist
```
JSON Request Body

{
    "name": "New Artist Name",
    "instagram": "https://www.instagram.com/artistnameofficial/",
    "twitter": "https://twitter.com/artistnameofficial"
}
```
- GET all artists: /artist
- GET one artist: /artist/1
- UPDATE (PUT) one artist: artist/1
```
JSON Request Body

{
    "id": 1,
    "name": "Arist Name",
    "instagram": "https://www.instagram.com/artistnameofficial",
    "twitter": "https://twitter.com/artistnameofficial"
}
```
- DELETE one artist: /artist/1

## Album

- CREATE (POST) an album: /album
```
JSON Request Body

{
    "title": "New Album Name",
    "artistId": 1,
    "releaseDate": "2000-01-31",
    "labelId": 1,
    "listPrice": 9.99
}
```
- GET all albums: /album
- GET one album: /album/1
- UPDATE (PUT) one album: /album/1
```
JSON Request Body

{
    "id": 1,
    "title": "Blast Tyrant",
    "artistId": 1,
    "releaseDate": "2004-03-25",
    "labelId": 1,
    "listPrice": 9.99
}
```
- DELETE one album: /album/1

## Track
- CREATE (POST) a track: /track
```
JSON Request Body

{
    "albumId": 1,
    "title": "New Track Name",
    "runTime": 3
}
```
- GET all tracks: /track
- GET one track: /track/1
- UPDATE (PUT) one track: /track/1
```
JSON Request Body

{
    "id": 1,
    "albumId": 1,
    "title": "Track Name",
    "runTime": 4
}
```
- DELETE one track: /track/1

# Music Recommendations

## Label Recommendation
- CREATE (POST) a Label: /labelrec
```
JSON Request Body

{
    "labelId": 1,
    "userId": 1,
    "liked": false
}
```
- GET all labels: /labelrec
- GET one label: /labelrec/1
- UPDATE (PUT) one label: /labelrec/1
```
JSON Request Body

{
    "id": 1,
    "labelId": 1,
    "userId": 1,
    "liked": true
}
```
- DELETE a label: /labelrec/1

## Album Recommendation
- CREATE (POST) an album: /albumrec
```
JSON Request Body

{
    "albumId": 1,
    "userId": 1,
    "liked": true
}
```
- GET all albums: /albumrec
- GET one album: /albumrec/1
- UPDATE (PUT) one album: /albumrec/1
```
JSON Request Body

{
    "id": 1,
    "albumId": 1,
    "userId": 1,
    "liked": false
}
```
- DELETE one album: /albumrec/1

## Artist Recommendation
- CREATE (POST) an artist: /artistrec
```
JSON Request Body

{
    "artistId": 1,
    "userId": 1,
    "liked": true
}
```
- GET artists: /artistrec
- GET one artist: /artistrec/1
- UPDATE (PUT): /artistrec/1
```
JSON Request Body

{
    "id": 1,
    "artistId": 1,
    "userId": 1,
    "liked": true
}
```
- DELETE one artist: /artistrec/1

## Track Recommendation
- CREATE (POST) a track: /trackrec
```
JSON Request Body

{
    "userId": 1,
    "trackId": 1,
    "liked": false
}
```
- GET all tracks: /trackrec
- GET one track: /trackrec/1
- UPDATE (PUT) a track: /trackrec/1
```
JSON Request Body

{
    "id": 1,
    "trackId": 4,
    "userId": 1,
    "liked": true
}
```
- DELETE a track: /trackrec/1