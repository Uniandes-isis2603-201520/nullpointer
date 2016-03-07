(function (ng) {
    
    var mod = ng.module("multimediaModule");

    mod.service("multimediaService", [function () {
            
    var photos = [ 
            {src: 'http://bootstrapbay.com/blog/wp-content/uploads/2014/05/stocksnap-free-stock-photos1.jpg'},
            {src: 'http://bootstrapbay.com/blog/wp-content/uploads/2014/05/unslpash-desert-road_uvsq5s.png'},
            {src: 'http://bootstrapbay.com/blog/wp-content/uploads/2014/05/yellow-taxi_vvvjao.png'},
            {src: 'http://bootstrapbay.com/blog/wp-content/uploads/2014/05/negative-space.jpg'},
            {src: 'http://bootstrapbay.com/blog/wp-content/uploads/2014/05/SplitShire_air_balloons_gma6ks.jpg'}
            
        ];

        
     this.getPhotos = function () {
                return new Promise(function (resolve, reject) {
                    if (photos.length !== 0) {
                        resolve(photos);
                    } else {
                        reject("Error occurred");
                    }
                });
        };
        
        this.getSplitPhotos = function () {
                return new Promise(function (resolve, reject) {
                    if (photos.length !== 0) {
                       var cont=0;
                       var total=[];
                       var split=[];
                       for(var i=0;i<photos.length;i++)
                       {
                           var obj=photos[i];
                           split.push(obj);
                           if(cont===4)
                           {
                               total.push(split);
                               split=[];
                               cont=0;
                           }
                           else
                           {
                               cont++;
                           }
                           
                       }
                       resolve(total);
                       
                    } else {
                        reject("Error occurred");
                    }
                });
        };
        
      this.addPhoto = function (url) {
                return new Promise(function (resolve, reject) {
                    if (url!=null) {
                        var ingreso={src: url};
                        photos.push(ingreso);
                        resolve("Se agrego correctamente la foto.");
                    } else {
                        reject("Error occurred");
                    }
                });
        };
        
     this.deletePhoto=function(index)
     {
         return new Promise(function (resolve, reject) {
                    if (index>-1 || index<photos.length) {
                        photos=photos.splice(index, 1);
                        resolve("Se elimino correctamente la foto");
                    } else {
                        reject("No se pudo eliminar la foto!");
                    }
                });   
     };
            
            
       
             
    }]);
})(window.angular);