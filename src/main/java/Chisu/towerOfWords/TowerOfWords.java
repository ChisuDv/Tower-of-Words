package Chisu.towerOfWords;

import org.bukkit.plugin.java.JavaPlugin;

public final class TowerOfWords extends JavaPlugin {

    @Override
    public void onEnable() {



        //Mensaje de inicio correcto
        System.out.println("El Plugin Torres de Palabras fue activado correctamente");
    }

    @Override
    public void onDisable() {



        //Mensaje de salida
        System.out.println("El Plugin Torres de Palabras fue desactivado :c");
    }
}
