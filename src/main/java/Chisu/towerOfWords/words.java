package Chisu.towerOfWords;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class words implements CommandExecutor {

    private HashMap<Character, Material> letraABloque;

    // Constructor que inicializa el mapeo
    public words() {
        inicializarMapeoLetras();
    }

    private void inicializarMapeoLetras() {
        letraABloque = new HashMap<>();

        // Mapeo del alfabeto a diferentes bloques
        letraABloque.put('A', Material.WHITE_STAINED_GLASS);
        letraABloque.put('B', Material.LIGHT_GRAY_STAINED_GLASS);
        letraABloque.put('C', Material.GRAY_STAINED_GLASS);
        letraABloque.put('D', Material.BLACK_STAINED_GLASS);
        letraABloque.put('E', Material.BROWN_STAINED_GLASS);
        letraABloque.put('F', Material.RED_STAINED_GLASS);
        letraABloque.put('G', Material.ORANGE_STAINED_GLASS);
        letraABloque.put('H', Material.YELLOW_STAINED_GLASS);
        letraABloque.put('I', Material.LIME_STAINED_GLASS);
        letraABloque.put('J', Material.GREEN_STAINED_GLASS);
        letraABloque.put('K', Material.CYAN_STAINED_GLASS);
        letraABloque.put('L', Material.LIGHT_BLUE_STAINED_GLASS);
        letraABloque.put('M', Material.BLUE_STAINED_GLASS);
        letraABloque.put('N', Material.CYAN_CONCRETE);
        letraABloque.put('Ñ', Material.PURPLE_STAINED_GLASS);
        letraABloque.put('O', Material.MAGENTA_STAINED_GLASS);
        letraABloque.put('P', Material.PINK_STAINED_GLASS);
        letraABloque.put('Q', Material.WHITE_CONCRETE);
        letraABloque.put('R', Material.LIGHT_GRAY_CONCRETE);
        letraABloque.put('S', Material.GRAY_CONCRETE);
        letraABloque.put('T', Material.BLACK_CONCRETE);
        letraABloque.put('U', Material.BROWN_CONCRETE);
        letraABloque.put('V', Material.RED_CONCRETE);
        letraABloque.put('W', Material.ORANGE_CONCRETE);
        letraABloque.put('X', Material.YELLOW_CONCRETE);
        letraABloque.put('Y', Material.LIME_CONCRETE);
        letraABloque.put('Z', Material.GREEN_CONCRETE);

        // Espacio
        letraABloque.put(' ', Material.AIR);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        // Verificar que sea un jugador quien ejecuta el comando
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("§cEste comando solo puede ser ejecutado por un jugador!");
            return true;
        }

        Player player = (Player) commandSender;

        // Verificar que se haya proporcionado una palabra
        if (strings.length == 0) {
            player.sendMessage("§cUso correcto: /respuesta <palabra>");
            return true;
        }

        // Obtener la palabra completa (unir todos los argumentos)
        StringBuilder palabraBuilder = new StringBuilder();
        for (String arg : strings) {
            palabraBuilder.append(arg).append(" ");
        }
        String palabra = palabraBuilder.toString().trim().toUpperCase();

        // Validar longitud
        if (palabra.length() > 50) {
            player.sendMessage("§cLa palabra es demasiado larga! Máximo 50 caracteres.");
            return true;
        }

        // Obtener la ubicación del jugador
        Location ubicacionInicial = player.getLocation().clone();

        // Colocar los bloques verticalmente
        int altura = 0;
        for (char letra : palabra.toCharArray()) {
            Material bloque = letraABloque.get(letra);

            if (bloque == null) {
                player.sendMessage("§eAdvertencia: El carácter '" + letra + "' no tiene un bloque asignado.");
                continue;
            }

            // Calcular la posición del bloque (hacia arriba desde la ubicación del jugador)
            Location posicionBloque = ubicacionInicial.clone().add(0, altura, 0);

            // Verificar que no exceda el límite inferior del mundo
            if (posicionBloque.getY() < player.getWorld().getMinHeight()) {
                player.sendMessage("§cSe alcanzó el límite inferior del mundo!");
                break;
            }

            // Colocar el bloque
            posicionBloque.getBlock().setType(bloque);

            altura++; // Incrementar altura para la siguiente letra
        }

        // Teletransportar al jugador encima de la torre (altura total + 1 bloque extra)
        Location posicionFinal = ubicacionInicial.clone().add(0, altura + 1, 0);
        player.teleport(posicionFinal);

        player.sendMessage("§a¡Respuesta colocada con éxito! §7(" + palabra + ")");
        return true;
    }
}
