const ObjectId = require("mongodb").ObjectId;
const MongoClient = require('mongodb').MongoClient;
const url = "mongodb://localhost:27017/trackseries";

const dbName = "trackseries";
const collectionName = "shows";


var db;

MongoClient.connect(url, (err, client) => {
    if (err) return console.log(err);
    db = client.db(dbName);
});


module.exports = {
    getAllShows: function () {
        return db.collection(collectionName).find();
    },

    getShowById: function (showId) {
        return db.collection(collectionName).find({_id: new ObjectId(showId)})
    },

    insertShow: function (show) {
        db.collection(collectionName).insertOne(show);
    },

    deleteById: function(showId) {
        db.collection(collectionName).deleteOne({_id: new ObjectId(showId)})
    },

    updateShow: function(showId, newShow) {
        db.collection(collectionName).updateOne({_id: new ObjectId(showId)}, {$set: {title: newShow.title, producer: newShow.producer, description: newShow.description}})
    }
};



