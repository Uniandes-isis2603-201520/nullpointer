(function (ng) {
    var mod = ng.module("eventoModule");
    mod.service('EventosInfoService', ["$q", "$http", function ($q) {
            var events = [
                {
                    "id": 0,
                    "title": "Centro de Bogotá",
                    "image": "http://static.thousandwonders.net/Bogota.original.360.jpg",
                    "type": "Site",
                    "start": "--",
                    "end": "--",
                    "description": "Bogotá ha sido llamada \"La Atenas Sudamericana\", apodo que se fortaleció a finales del siglo XIX y principios del siglo XX, por iniciativa del escritor español Marcelino Menéndez Pelayo, debido a la gran admiración que tenía por los filósofos colombianos, entre ellos Rufino José Cuervo y Miguel Antonio Caro. 120 121 La viajero dispone de una amplia oferta cultural que se ha incrementado considerablemente en las últimas décadas, además de ser la sede de importantes festivales de amplia trayectoria y reconocimiento nacional e internacional",
                    "comments": [
                        {
                            "user": "Usuario1",
                            "userPhoto": "http://www.periodicoabc.mx/sites/default/files/anonimoface.png",
                            "stars": 5,
                            "comment": "Todo bien"
                        },
                        {
                            "user": "Usuario2",
                            "userPhoto": "http://www.periodicoabc.mx/sites/default/files/anonimoface.png",
                            "stars": 3,
                            "comment": "Bien"
                        },
                        {
                            "user": "Usuario3",
                            "userPhoto": "http://www.periodicoabc.mx/sites/default/files/anonimoface.png",
                            "stars": 4,
                            "comment": "Normal"
                        },
                        {
                            "user": "Usuario4",
                            "userPhoto": "http://www.periodicoabc.mx/sites/default/files/anonimoface.png",
                            "stars": 2,
                            "comment": "No muy bien"
                        },
                        {
                            "user": "Usuario5",
                            "userPhoto": "http://www.periodicoabc.mx/sites/default/files/anonimoface.png",
                            "stars": 1,
                            "comment": "Horrible"
                        }
                    ]
                },
                {
                    "id": 1,
                    "title": "Monserrate",
                    "image": "http://zonacbogota.com/wp-content/uploads/2014/04/monserrate.jpg",
                    "type": "Site",
                    "start": "--",
                    "end": "--",
                    "description": "La catedral de monserrate es muy bonita. Se sube a pie, en teleferico, en tranvia o de rodillas.",
                    "comments": [
                        {
                            "user": "Usuario1",
                            "userPhoto": "http://www.periodicoabc.mx/sites/default/files/anonimoface.png",
                            "stars": 5,
                            "comment": "Todo bien"
                        },
                        {
                            "user": "Usuario2",
                            "userPhoto": "http://www.periodicoabc.mx/sites/default/files/anonimoface.png",
                            "stars": 3,
                            "comment": "Bien"
                        },
                        {
                            "user": "Usuario3",
                            "userPhoto": "http://www.periodicoabc.mx/sites/default/files/anonimoface.png",
                            "stars": 4,
                            "comment": "Normal"
                        },
                        {
                            "user": "Usuario4",
                            "userPhoto": "http://www.periodicoabc.mx/sites/default/files/anonimoface.png",
                            "stars": 2,
                            "comment": "No muy bien"
                        },
                        {
                            "user": "Usuario5",
                            "userPhoto": "http://www.periodicoabc.mx/sites/default/files/anonimoface.png",
                            "stars": 1,
                            "comment": "Horrible"
                        }
                    ]
                },
                {
                    "id": 2,
                    "title": "Concierto",
                    "image": "http://imagenes.interlatin.com/sdi/2015/11/20/6abbfe2cfaf64c5ca1cf6dacf6b5510a.jpg",
                    "type": "Event",
                    "start": "1460579400000",
                    "end": "1460599400000",
                    "description": "Coldplay, una de las bandas mas esperadas, llega este año a Bogotá.",
                    "comments": [
                        {
                            "user": "Usuario1",
                            "userPhoto": "http://www.periodicoabc.mx/sites/default/files/anonimoface.png",
                            "stars": 5,
                            "comment": "Todo bien"
                        },
                        {
                            "user": "Usuario2",
                            "userPhoto": "http://www.periodicoabc.mx/sites/default/files/anonimoface.png",
                            "stars": 3,
                            "comment": "Bien"
                        },
                        {
                            "user": "Usuario3",
                            "userPhoto": "http://www.periodicoabc.mx/sites/default/files/anonimoface.png",
                            "stars": 4,
                            "comment": "Normal"
                        },
                        {
                            "user": "Usuario4",
                            "userPhoto": "http://www.periodicoabc.mx/sites/default/files/anonimoface.png",
                            "stars": 2,
                            "comment": "No muy bien"
                        },
                        {
                            "user": "Usuario5",
                            "userPhoto": "http://www.periodicoabc.mx/sites/default/files/anonimoface.png",
                            "stars": 1,
                            "comment": "Horrible"
                        }
                    ]
                },
                {
                    "id": 3,
                    "title": "Evento sorpresa",
                    "image": "http://imagenes.interlatin.com/sdi/2015/11/20/6abbfe2cfaf64c5ca1cf6dacf6b5510a.jpg",
                    "type": "Event",
                    "start": "1460579400000",
                    "end": "1460599400000",
                    "description": "Un evento que no te puedes perder!",
                    "comments": [
                        {
                            "user": "Usuario5",
                            "userPhoto": "http://www.periodicoabc.mx/sites/default/files/anonimoface.png",
                            "stars": 1,
                            "comment": "Horrible"
                        }
                    ]
                },
                {
                    "id": 4,
                    "title": "Evento sorpresa",
                    "image": "http://imagenes.interlatin.com/sdi/2015/11/20/6abbfe2cfaf64c5ca1cf6dacf6b5510a.jpg",
                    "type": "Event",
                    "start": "1460579400000",
                    "end": "1460599400000",
                    "description": "Un evento que no te puedes perder!",
                    "comments": [
                        {
                            "user": "Usuario5",
                            "userPhoto": "http://www.periodicoabc.mx/sites/default/files/anonimoface.png",
                            "stars": 1,
                            "comment": "Horrible"
                        }
                    ]
                },
                {
                    "id": 5,
                    "title": "Evento sorpresa",
                    "image": "http://imagenes.interlatin.com/sdi/2015/11/20/6abbfe2cfaf64c5ca1cf6dacf6b5510a.jpg",
                    "type": "Event",
                    "start": "1460579400000",
                    "end": "1460599400000",
                    "description": "Un evento que no te puedes perder!",
                    "comments": [
                        {
                            "user": "Usuario5",
                            "userPhoto": "http://www.periodicoabc.mx/sites/default/files/anonimoface.png",
                            "stars": 1,
                            "comment": "Horrible"
                        }
                    ]
                },
                {
                    "id": 6,
                    "title": "Evento sorpresa",
                    "image": "http://imagenes.interlatin.com/sdi/2015/11/20/6abbfe2cfaf64c5ca1cf6dacf6b5510a.jpg",
                    "type": "Event",
                    "start": "1460579400000",
                    "end": "1460599400000",
                    "description": "Un evento que no te puedes perder!",
                    "comments": [
                        {
                            "user": "Usuario5",
                            "userPhoto": "http://www.periodicoabc.mx/sites/default/files/anonimoface.png",
                            "stars": 1,
                            "comment": "Horrible"
                        }
                    ]
                },
                {
                    "id": 7,
                    "title": "Evento sorpresa",
                    "image": "http://imagenes.interlatin.com/sdi/2015/11/20/6abbfe2cfaf64c5ca1cf6dacf6b5510a.jpg",
                    "type": "Event",
                    "start": "1460579400000",
                    "end": "1460599400000",
                    "description": "Un evento que no te puedes perder!",
                    "comments": [
                        {
                            "user": "Usuario5",
                            "userPhoto": "http://www.periodicoabc.mx/sites/default/files/anonimoface.png",
                            "stars": 1,
                            "comment": "Horrible"
                        }
                    ]
                },
                {
                    "id": 8,
                    "title": "Evento sorpresa",
                    "image": "http://imagenes.interlatin.com/sdi/2015/11/20/6abbfe2cfaf64c5ca1cf6dacf6b5510a.jpg",
                    "type": "Event",
                    "start": "1460579400000",
                    "end": "1460599400000",
                    "description": "Un evento que no te puedes perder!",
                    "comments": [
                        {
                            "user": "Usuario5",
                            "userPhoto": "http://www.periodicoabc.mx/sites/default/files/anonimoface.png",
                            "stars": 1,
                            "comment": "Horrible"
                        }
                    ]
                }

            ];
            this.fetchEventos = function () {
                return $q(function (resolve, reject) {
                    setTimeout(function () {
                        if (true) {
                            resolve(events);
                        } else {
                            reject('Error.');
                        }
                    }, 1000);
                });
            };
            this.saveRecord = function (newComment, id) {
                var nc = newComment;
                var i = id;
                console.log(nc);
                console.log(i);
                return $q(function (resolve, reject) {
                    setTimeout(function () {
                        if (true) {
                            resolve(
                                    ng.forEach(events, function (value) {
                                        if (value.id === id) {
                                            value.comments.push(nc);
                                        }
                                    }
                                    )
                                    );
                        } else {
                            reject('Error.');
                        }
                    }, 1000);
                });
            };
        }]);
})(window.angular);

