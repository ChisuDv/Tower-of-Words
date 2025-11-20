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
        letraABloque.put('A', Material.GRAY_STAINED_GLASS);
        letraABloque.put('B', Material.BLUE_WOOL);
        letraABloque.put('C', Material.RED_CONCRETE);
        letraABloque.put('D', Material.GREEN_WOOL);
        letraABloque.put('E', Material.YELLOW_CONCRETE);
        letraABloque.put('F', Material.PURPLE_WOOL);
        letraABloque.put('G', Material.ORANGE_CONCRETE);
        letraABloque.put('H', Material.WHITE_WOOL);
        letraABloque.put('I', Material.LIGHT_BLUE_CONCRETE);
        letraABloque.put('J', Material.MAGENTA_WOOL);
        letraABloque.put('K', Material.LIME_CONCRETE);
        letraABloque.put('L', Material.PINK_WOOL);
        letraABloque.put('M', Material.CYAN_CONCRETE);
        letraABloque.put('N', Material.BROWN_WOOL);
        letraABloque.put('O', Material.BLACK_CONCRETE);
        letraABloque.put('P', Material.RED_WOOL);
        letraABloque.put('Q', Material.GREEN_CONCRETE);
        letraABloque.put('R', Material.BLUE_CONCRETE);
        letraABloque.put('S', Material.YELLOW_WOOL);
        letraABloque.put('T', Material.LIGHT_GRAY_CONCRETE);
        letraABloque.put('U', Material.ORANGE_WOOL);
        letraABloque.put('V', Material.PURPLE_CONCRETE);
        letraABloque.put('W', Material.LIME_WOOL);
        letraABloque.put('X', Material.PINK_CONCRETE);
        letraABloque.put('Y', Material.GRAY_CONCRETE);
        letraABloque.put('Z', Material.CYAN_WOOL);

        // Números
        letraABloque.put('0', Material.STONE);
        letraABloque.put('1', Material.COBBLESTONE);
        letraABloque.put('2', Material.STONE_BRICKS);
        letraABloque.put('3', Material.MOSSY_COBBLESTONE);
        letraABloque.put('4', Material.ANDESITE);
        letraABloque.put('5', Material.DIORITE);
        letraABloque.put('6', Material.GRANITE);
        letraABloque.put('7', Material.POLISHED_ANDESITE);
        letraABloque.put('8', Material.POLISHED_DIORITE);
        letraABloque.put('9', Material.POLISHED_GRANITE);

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
