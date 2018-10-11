-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1:3306
-- Tiempo de generación: 07-03-2017 a las 03:17:16
-- Versión del servidor: 10.1.13-MariaDB
-- Versión de PHP: 5.6.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `check_goals_db`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `metas`
--

CREATE TABLE `metas` (
  `id_meta` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `nombre` varchar(50) COLLATE utf8_spanish2_ci NOT NULL,
  `nota_acumulada` int(11) NOT NULL DEFAULT '0',
  `nota_evaluada` int(11) NOT NULL DEFAULT '0',
  `nota_minima` int(11) NOT NULL,
  `lograda` tinyint(1) NOT NULL DEFAULT '0',
  `finalizada` tinyint(1) NOT NULL DEFAULT '0',
  `fecha` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Volcado de datos para la tabla `metas`
--

INSERT INTO `metas` (`id_meta`, `id_usuario`, `nombre`, `nota_acumulada`, `nota_evaluada`, `nota_minima`, `lograda`, `finalizada`, `fecha`) VALUES
(1, 1, 'fisica', 70, 100, 55, 1, 1, '2016-07-06'),
(2, 1, 'matematica', 40, 80, 55, 0, 0, '2016-07-21'),
(4, 1, 'quimica', 25, 90, 55, 0, 0, '2016-07-04'),
(5, 1, 'quimica2', 25, 100, 55, 0, 1, '2000-05-06'),
(6, 1, 'dibujo', 60, 80, 55, 0, 0, '2014-08-13'),
(7, 8, 'desarrollo', 100, 100, 55, 1, 1, '2016-07-22');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id_usuario` int(11) NOT NULL,
  `nombre` varchar(20) COLLATE utf8_spanish2_ci NOT NULL,
  `apellido` varchar(20) COLLATE utf8_spanish2_ci NOT NULL,
  `anio_nacimiento` year(4) NOT NULL,
  `correo` varchar(50) COLLATE utf8_spanish2_ci NOT NULL,
  `clave` varchar(50) COLLATE utf8_spanish2_ci NOT NULL,
  `metas_creadas` int(11) NOT NULL DEFAULT '0',
  `metas_eliminadas` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id_usuario`, `nombre`, `apellido`, `anio_nacimiento`, `correo`, `clave`, `metas_creadas`, `metas_eliminadas`) VALUES
(1, 'stalin', 'sanchez', 1994, 'stalin@gmail.com', '123456', 6, 3),
(4, 'beatriz', 'lopez', 1995, 'loyher@gmail.com', '123456', 0, 0),
(5, 'juan', 'perez', 0000, 'juan@gmail.com', '123456', 0, 0),
(6, 'jose', 'leon', 1980, 'jose@hdh.com', '123456', 0, 0),
(8, 'jonathan', 'cuotto', 1994, 'jonathan@gmail.com', '12346', 1, 0);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `metas`
--
ALTER TABLE `metas`
  ADD PRIMARY KEY (`id_meta`),
  ADD KEY `id_usuario` (`id_usuario`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id_usuario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `metas`
--
ALTER TABLE `metas`
  MODIFY `id_meta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id_usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `metas`
--
ALTER TABLE `metas`
  ADD CONSTRAINT `metas_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`) ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
