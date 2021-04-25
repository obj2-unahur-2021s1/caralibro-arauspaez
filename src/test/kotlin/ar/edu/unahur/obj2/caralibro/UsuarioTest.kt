package ar.edu.unahur.obj2.caralibro

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNot
import io.kotest.matchers.shouldNotBe

class UsuarioTest : DescribeSpec({
  describe("Caralibro") {

    //Publicaciones
    val saludoCumpleanios = Texto("Felicidades Pepito, que los cumplas muy feliz")
    val fotoEnCuzco = Foto(768, 1024)
    //Nuevas
    val fotoLucho = Foto(10, 10)
    val textoLucho = Texto("Texto generico")
    val videoLucho = Video(Calidad.SD, 60)

    val fotoJulian = Foto(10, 10)
    val textoJulian = Texto("Texto generico")
    val videoJulian = Video(Calidad.SD, 60)

    val fotoFede = Foto(10, 10)
    val textoFede = Texto("Texto generico")
    val videoFede = Video(Calidad.SD, 60)

    //Usuarios
    val lucho = Usuario()
    val julian = Usuario()
    val fede = Usuario()

    //Agrega publi a Lucho
    lucho.agregarPublicacion(videoLucho)
    lucho.agregarPublicacion(videoLucho)
    lucho.agregarPublicacion(videoLucho)
    julian.agregarPublicacion(fotoJulian)
    julian.agregarPublicacion(textoJulian)
    julian.agregarPublicacion(videoJulian)
    fede.agregarPublicacion(fotoFede)
    fede.agregarPublicacion(textoFede)
    fede.agregarPublicacion(videoFede)

    //REQ 1 - Saber cuánto espacio ocupa el total de las publicaciones de un usuario.
    describe("Una publicación") {
      describe("de tipo foto") {
        it("ocupa ancho * alto * compresion bytes") {
          fotoEnCuzco.espacioQueOcupa().shouldBe(550503)
        }
        it("NO ocupa ancho * alto * compresion bytes") {
          fotoEnCuzco.espacioQueOcupa().shouldNotBe(225987)
        }
      }

      describe("de tipo texto") {
        it("ocupa tantos bytes como su longitud") {
          saludoCumpleanios.espacioQueOcupa().shouldBe(45)
        }
        it("NO ocupa tantos bytes como su longitud") {
          saludoCumpleanios.espacioQueOcupa().shouldNotBe(25)
        }
      }

      describe("de tipo video") {
        it("ocupa tantos bytes como su longitud en SD") {
          videoLucho.espacioQueOcupa().shouldBe(60)
        }
        it("ocupa tantos bytes como su longitud en HD720") {
          videoLucho.cambiarCalidad(Calidad.HD720)
          videoLucho.espacioQueOcupa().shouldBe(180)
        }
        it("ocupa tantos bytes como su longitud en HD1080") {
          videoLucho.cambiarCalidad(Calidad.HD1080)
          videoLucho.espacioQueOcupa().shouldBe(360)
        }
        it("NO ocupa tantos bytes como su longitud en SD") {
          videoLucho.espacioQueOcupa().shouldNotBe(100)
        }
        it("NO ocupa tantos bytes como su longitud en HD720") {
          videoLucho.cambiarCalidad(Calidad.HD720)
          videoLucho.espacioQueOcupa().shouldNotBe(200)
        }
        it("NO ocupa tantos bytes como su longitud en HD1080") {
          videoLucho.cambiarCalidad(Calidad.HD1080)
          videoLucho.espacioQueOcupa().shouldNotBe(300)
        }
      }
    }
    describe("Un usuario2") {
      it("puede calcular el espacio que ocupan sus publicaciones") {
        val juana = Usuario()
        juana.agregarPublicacion(fotoEnCuzco)
        juana.agregarPublicacion(saludoCumpleanios)
        juana.espacioDePublicaciones().shouldBe(550548)
      }
    }

    //REQ 2 -
    describe("Poder darle me gusta a una publicación, y conocer cuántas veces fue votada de esta forma.") { //Poder darle me gusta a una publicación, y conocer cuántas veces fue votada de esta forma.
      it("puede darle me gusta a una publicacion") {
        lucho.meLikea(videoLucho,julian)
        videoLucho.cantidadLikes().shouldBe(1)
      }
      it("No puede darle me gusta a una publicacion mas de 1 vez"){
        lucho.meLikea(videoLucho,julian)
        lucho.meLikea(videoLucho,julian)
        videoLucho.cantidadLikes().shouldNotBe(2)
      }
    }

    //REQ 3
    describe("Saber si un usuario es más amistoso que otro: esto ocurre cuando el primero tiene más amigos") { //Poder darle me gusta a una publicación, y conocer cuántas veces fue votada de esta forma.
      it("Un usuario es mas amistoso que otro") {
        lucho.agregarAmigo(julian)
        lucho.agregarAmigo(fede)
        lucho.esMasAmistosoQue(julian).shouldBe(true)
      }
      it("Un usuario NO es mas amistoso que otro") {
        lucho.agregarAmigo(julian)
        julian.agregarAmigo(fede)
        lucho.esMasAmistosoQue(julian).shouldBe(false)
      }
    }

    //Retomar despues. Hay un quilombo con puedeVerPublicacion
    /*describe("Saber si un usuario puede ver una cierta publicacion."){
      it("puede ver una publicacion publica"){
        videoLucho.puedeVerPublicacion(julian,lucho).shouldBe(true)
      }
      it("puede ver una publicacion solo amigos"){
        videoLucho.puedeVerPublicacion(julian,lucho).shouldBe(true)
      }
      it("puede ver una publicacion privado con lista de permitidos"){
        videoLucho.puedeVerPublicacion(julian,lucho).shouldBe(true)
      }
      it("puede ver una publicacion privado con lista de excluidos"){
        videoLucho.puedeVerPublicacion(julian,lucho).shouldBe(true)
      }
    }
    */
    /*  describe("Determinar los mejores amigos de un usuario: el conjunto de sus amigos que pueden ver todas sus publicaciones."){

      it(""){

      }
    }*/
    describe("El amigo más popular que tiene un usuario. Es decir, el amigo que tiene mas me gusta entre todas sus publicaciones.") {
      it("Es el amigo mas popular") {
        julian.agregarAmigo(fede)
        julian.agregarAmigo(lucho)
        fede.agregarAmigo(lucho)
        lucho.meLikea(videoLucho, julian)
        lucho.meLikea(videoLucho, fede)
        julian.meLikea(videoJulian, lucho)
        fede.amigoMasPopular().shouldBe(lucho)
      }

      it("NO es el amigo mas popular") {
        julian.agregarAmigo(fede)
        julian.agregarAmigo(lucho)
        fede.agregarAmigo(lucho)
        lucho.meLikea(videoLucho, julian)
        lucho.meLikea(videoLucho, fede)
        julian.meLikea(videoJulian, lucho)
        fede.amigoMasPopular().shouldNotBe(julian)
      }
    }

    describe("Un usuario stalkea a otro. Lo cual ocurre si el stalker le dio me gusta a más del 90% de sus publicaciones"){
      it("es Stalker"){
        julian.agregarAmigo(lucho)
        fede.agregarAmigo(lucho)
        lucho.meLikea(textoLucho,julian)
        lucho.meLikea(fotoLucho,julian)
        lucho.meLikea(videoLucho,julian)
        lucho.esStalker(julian).shouldBe(true)
      }
      it("NO es Stalker"){
        julian.agregarAmigo(fede)
        fede.agregarAmigo(lucho)
        lucho.meLikea(textoLucho,fede)
        lucho.meLikea(fotoLucho,fede)
        lucho.esStalker(fede).shouldBe(false)
      }
    }
  }
})
