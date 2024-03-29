PGDMP                 
        w            4Linea    11.3    11.3                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                       false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                       false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                       false                       1262    16386    4Linea    DATABASE     �   CREATE DATABASE "4Linea" WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Spanish_Spain.1252' LC_CTYPE = 'Spanish_Spain.1252';
    DROP DATABASE "4Linea";
             postgres    false            �            1259    16387    familia    TABLE     w   CREATE TABLE public.familia (
    apellido character varying(20) NOT NULL,
    numero_integrantes smallint NOT NULL
);
    DROP TABLE public.familia;
       public         postgres    false            �            1259    16392    persona    TABLE     �   CREATE TABLE public.persona (
    identificacion character varying(20) NOT NULL,
    nombre character varying(150) NOT NULL,
    edad smallint NOT NULL,
    familia character varying(20) NOT NULL,
    padre character varying(20)
);
    DROP TABLE public.persona;
       public         postgres    false            �            1259    16407    tipo_usuario    TABLE     s   CREATE TABLE public.tipo_usuario (
    codigo integer NOT NULL,
    descripcion character varying(255) NOT NULL
);
     DROP TABLE public.tipo_usuario;
       public         postgres    false            �            1259    16412    usuario    TABLE     �   CREATE TABLE public.usuario (
    correo character varying(150) NOT NULL,
    contrasena character varying(30) NOT NULL,
    tipo_usuario integer NOT NULL,
    persona character varying(20) NOT NULL
);
    DROP TABLE public.usuario;
       public         postgres    false                      0    16387    familia 
   TABLE DATA               ?   COPY public.familia (apellido, numero_integrantes) FROM stdin;
    public       postgres    false    196                    0    16392    persona 
   TABLE DATA               O   COPY public.persona (identificacion, nombre, edad, familia, padre) FROM stdin;
    public       postgres    false    197   @                 0    16407    tipo_usuario 
   TABLE DATA               ;   COPY public.tipo_usuario (codigo, descripcion) FROM stdin;
    public       postgres    false    198   �                 0    16412    usuario 
   TABLE DATA               L   COPY public.usuario (correo, contrasena, tipo_usuario, persona) FROM stdin;
    public       postgres    false    199   �       �
           2606    16391    familia pk_familia 
   CONSTRAINT     V   ALTER TABLE ONLY public.familia
    ADD CONSTRAINT pk_familia PRIMARY KEY (apellido);
 <   ALTER TABLE ONLY public.familia DROP CONSTRAINT pk_familia;
       public         postgres    false    196            �
           2606    16396    persona pk_persona 
   CONSTRAINT     \   ALTER TABLE ONLY public.persona
    ADD CONSTRAINT pk_persona PRIMARY KEY (identificacion);
 <   ALTER TABLE ONLY public.persona DROP CONSTRAINT pk_persona;
       public         postgres    false    197            �
           2606    16416    usuario pk_usuario 
   CONSTRAINT     T   ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT pk_usuario PRIMARY KEY (correo);
 <   ALTER TABLE ONLY public.usuario DROP CONSTRAINT pk_usuario;
       public         postgres    false    199            �
           2606    16411    tipo_usuario tipo_usuario_pkey 
   CONSTRAINT     `   ALTER TABLE ONLY public.tipo_usuario
    ADD CONSTRAINT tipo_usuario_pkey PRIMARY KEY (codigo);
 H   ALTER TABLE ONLY public.tipo_usuario DROP CONSTRAINT tipo_usuario_pkey;
       public         postgres    false    198            �
           2606    16397    persona fk_persona_familia    FK CONSTRAINT     �   ALTER TABLE ONLY public.persona
    ADD CONSTRAINT fk_persona_familia FOREIGN KEY (familia) REFERENCES public.familia(apellido);
 D   ALTER TABLE ONLY public.persona DROP CONSTRAINT fk_persona_familia;
       public       postgres    false    2696    196    197            �
           2606    16402    persona fk_persona_persona    FK CONSTRAINT     �   ALTER TABLE ONLY public.persona
    ADD CONSTRAINT fk_persona_persona FOREIGN KEY (padre) REFERENCES public.persona(identificacion);
 D   ALTER TABLE ONLY public.persona DROP CONSTRAINT fk_persona_persona;
       public       postgres    false    197    197    2698            �
           2606    16422    usuario fk_usuario_persona    FK CONSTRAINT     �   ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT fk_usuario_persona FOREIGN KEY (persona) REFERENCES public.persona(identificacion);
 D   ALTER TABLE ONLY public.usuario DROP CONSTRAINT fk_usuario_persona;
       public       postgres    false    2698    197    199            �
           2606    16417    usuario fk_usuario_tipousuario    FK CONSTRAINT     �   ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT fk_usuario_tipousuario FOREIGN KEY (tipo_usuario) REFERENCES public.tipo_usuario(codigo);
 H   ALTER TABLE ONLY public.usuario DROP CONSTRAINT fk_usuario_tipousuario;
       public       postgres    false    199    2700    198                  x�+N�K�H��44����� ,/         B   x�341265���99��&��ŉy��U�1~\��F�&F��F���%���FiC�V�=... ��{         &   x�3�tL����,.)JL�/���2��*M��b���� ��
         .   x��M,I�w((*MMJ�K���4426�4�4402�M��b���� ��
     