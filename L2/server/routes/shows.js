const express = require('express');
const router = express.Router();
const database = require('../mongo');

router.get('/shows', function(req, res, next) {
    database.getAllShows().toArray((err, data) => {
        var data2 = [];
        for (let i = 0; i < data.length; i++)
        {
            var show = data[i];
            var x = {
                id: show._id,
                title: show.title,
                producer: show.producer,
                description: show.description,
                rating: show.rating
            };
            data2.push(x)
        }
        res.send(data2);
    });
});


router.get('/shows/:id', function(req, res, next) {
    const id = req.params.id;
    database.getShowById(id).toArray((err, data) => {
        res.send(data[0]);
    });
});

router.post('/shows', function(req, res, next) {
    console.log(req.body);
    database.insertShow(req.body);
    res.send(true);
});

router.delete('/shows/:id', function(req, res, next) {
    const id = req.params.id;
    database.deleteById(id);
    res.send(true);
});

router.put('/shows/:id', function(req, res, next) {
    const id = req.params.id;
    const newShow = req.body;
    database.updateShow(id, newShow);
    res.send(true);
});

module.exports = router;
