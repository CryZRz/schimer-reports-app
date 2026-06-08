{
  description = "JavaFX development environment";

  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";
  };

  outputs = { self, nixpkgs }:
    let
      system = "x86_64-linux"; # Adjust if you are on ARM/Mac
      pkgs = import nixpkgs { inherit system; };

      # Define the libraries JavaFX needs
      nativeLibs = with pkgs; [
        xorg.libX11
        xorg.libXxf86vm
        xorg.libXtst
        xdg-utils
        glib
        libGL
        pango
        gtk3
        glib
        gsettings-desktop-schemas
      ];
    in
    {
      devShells.${system}.default = pkgs.mkShell {
        buildInputs = with pkgs; [
          jdk21
          maven
        ] ++ nativeLibs;

        shellHook = ''
          export LD_LIBRARY_PATH=${pkgs.lib.makeLibraryPath nativeLibs}
          export XDG_DATA_DIRS="${pkgs.gsettings-desktop-schemas}/share/gsettings-schemas/${pkgs.gsettings-desktop-schemas.name}:${pkgs.gtk3}/share/gsettings-schemas/${pkgs.gtk3.name}:$XDG_DATA_DIRS"
        '';
      };
    };
}