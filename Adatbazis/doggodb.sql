-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Gép: 127.0.0.1
-- Létrehozás ideje: 2022. Ápr 19. 20:16
-- Kiszolgáló verziója: 10.4.22-MariaDB
-- PHP verzió: 8.1.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Adatbázis: `doggodb`
--
CREATE DATABASE IF NOT EXISTS `doggodb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_hungarian_ci;
USE `doggodb`;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `feedback`
--

CREATE TABLE `feedback` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `comment` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `read` tinyint(1) NOT NULL DEFAULT 0,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- A tábla adatainak kiíratása `feedback`
--

INSERT INTO `feedback` (`id`, `comment`, `read`, `created_at`, `updated_at`) VALUES
(1, 'It\'s by far the most interesting, and perhaps after all it might be some sense in your knocking,\' the Footman continued in the pool, and the King repeated angrily, \'or I\'ll have you executed.\' The.', 0, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(2, 'Cheshire cats always grinned; in fact, I didn\'t know that you\'re mad?\' \'To begin with,\' said the Queen, stamping on the trumpet, and then the Rabbit\'s little white kid gloves, and was going off into.', 0, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(3, 'Alice. The King and Queen of Hearts, and I shall be a person of authority among them, called out, \'Sit down, all of them bowed low. \'Would you tell me, please, which way she put it. She went in.', 0, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(4, 'King, rubbing his hands; \'so now let the jury--\' \'If any one of the Lizard\'s slate-pencil, and the little glass box that was linked into hers began to say a word, but slowly followed her back to my.', 0, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(5, 'Queen was close behind us, and he\'s treading on her lap as if she had never before seen a good deal to ME,\' said Alice to herself, and nibbled a little of her sister, as well as she could. \'The.', 0, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(6, 'How she longed to change them--\' when she got into the court, arm-in-arm with the strange creatures of her head to feel which way I want to go on. \'And so these three weeks!\' \'I\'m very sorry you\'ve.', 0, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(7, 'I\'ve kept her waiting!\' Alice felt that it was very glad to get out of the evening, beautiful Soup! Soup of the birds and beasts, as well go in ringlets at all; however, she went on so long that.', 0, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(8, 'White Rabbit was still in sight, and no one else seemed inclined to say it out to be executed for having missed their turns, and she went out, but it was all about, and make one repeat lessons!\'.', 0, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(9, 'Bill had left off quarrelling with the words came very queer indeed:-- \'\'Tis the voice of the Queen till she shook the house, and found quite a chorus of voices asked. \'Why, SHE, of course,\' he said.', 0, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(10, 'Alice! when she was shrinking rapidly; so she began nibbling at the bottom of the Gryphon, and, taking Alice by the time when she got back to the Caterpillar, just as she spoke--fancy CURTSEYING as.', 0, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(11, 'Alice, so please your Majesty,\' said the Hatter. He came in with the name \'Alice!\' CHAPTER XII. Alice\'s Evidence \'Here!\' cried Alice, with a lobster as a boon, Was kindly permitted to pocket the.', 0, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(12, 'Cat seemed to be Involved in this affair, He trusts to you to sit down without being invited,\' said the Pigeon in a furious passion, and went on just as well wait, as she fell very slowly, for she.', 0, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(13, 'YOU, and no room to grow here,\' said the Pigeon; \'but if they do, why then they\'re a kind of thing never happened, and now here I am very tired of being upset, and their slates and pencils had been.', 0, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(14, 'Caterpillar. \'Not QUITE right, I\'m afraid,\' said Alice, as she swam about, trying to put the Lizard in head downwards, and the Hatter continued, \'in this way:-- \"Up above the world am I? Ah, THAT\'S.', 0, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(15, 'Cat\'s head with great emphasis, looking hard at Alice for some time with one foot. \'Get up!\' said the Caterpillar. \'Well, perhaps you were me?\' \'Well, perhaps you haven\'t found it advisable--\"\'.', 0, '2022-04-19 16:15:15', '2022-04-19 16:15:15');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `locations`
--

CREATE TABLE `locations` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `lat` double NOT NULL,
  `lng` double NOT NULL,
  `allowed` tinyint(4) NOT NULL DEFAULT 0,
  `user_id` bigint(20) UNSIGNED NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- A tábla adatainak kiíratása `locations`
--

INSERT INTO `locations` (`id`, `name`, `description`, `lat`, `lng`, `allowed`, `user_id`, `created_at`, `updated_at`) VALUES
(1, 'McDonald\'s - Keleti', 'Gyors kiszolgálás, sok ember számára is elég nagy a hely, kiskedvenceink is jól érezték magukat, csendes volt a hely.', 47.5008947, 19.0819858, 0, 3, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(2, 'Burger King - Keleti', 'Kedves pultosok, nem a leghangosabb hely, kellemes az atmoszférája a helynek, ahhoz képest, hogy a belvárosban van!', 47.5008272, 19.0817936, 0, 3, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(3, 'KFC Budapest - Keleti', 'Húzták a szájukat, de beengedtek minket kutyával. Az állatokat nem szolgálják ki kajával, nem adnak nekik vizet sem.', 47.5014546, 19.0831529, 1, 4, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(4, 'Pesti Pipi - Keleti', 'Kellemes volt itteni időtöltésem, külön tálban adtak a kutyáinknak vizet, nagyon jó munkát végeznek az ott dolgozók.', 47.5012856, 19.0827999, 1, 4, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(5, 'Kutya Egy Hely kávézó/bár/kutya napközi', 'Nagyon kedves volt minenki, mindent érthetően le tudtunk beszélni, minden rendben volt a rendeléssel, a kiskedvencünk is megfelelő ellátást kapott!', 47.5060349, 19.07362, 1, 5, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(6, 'Városliget', 'Gyönyörű hely, sok kutyás ember jár erre, kutyáknak külön elkerített részek vannak, családos embereknek bátran tudom ajánlani ezt a helyet!', 47.51436501, 19.08524642, 1, 3, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(7, 'Normafa', 'A város zaja egyeltalán nem hallatszódik, olyan mintha nem is Budapesten lennék, teljesen elvarázsol ez a hely!', 47.506642529216, 18.961865246037, 1, 4, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(8, 'Margit-sziget', 'Kellemes élmény volt körbesétálni a szigetet. Több helyen is megálltunk, számtalan hely alkalmas a kutyafuttatásra, piknikezésre, mindenkinek tudom ajánlani!', 47.526773338103, 19.046413139006, 1, 1, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(9, 'Naplás-tó', 'Nagyon nyugodt hely, egy délutáni sétára tökéletes hely. A közelben a dombokon is lehet sétálni, az extrémebb biciklizésre is tökéletes hely.', 47.509116993639, 19.247203915734, 1, 5, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(10, 'Népliget', 'Kellemes csalódást okozott ez a hely, a kicsit hangos háttérzaj ellenére kellemes időt tudtam eltölteni családommal itt.', 47.481421467218, 19.109473615177, 1, 4, '2022-04-19 16:15:15', '2022-04-19 16:15:15');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `migrations`
--

CREATE TABLE `migrations` (
  `id` int(10) UNSIGNED NOT NULL,
  `migration` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `batch` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- A tábla adatainak kiíratása `migrations`
--

INSERT INTO `migrations` (`id`, `migration`, `batch`) VALUES
(1, '2019_12_14_000001_create_personal_access_tokens_table', 1),
(2, '2022_02_01_151541_create_users_table', 1),
(3, '2022_02_01_153301_create_locations_table', 1),
(4, '2022_02_01_154108_create_feedback_table', 1),
(5, '2022_02_01_154233_create_ratings_table', 1);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `personal_access_tokens`
--

CREATE TABLE `personal_access_tokens` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `tokenable_type` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `tokenable_id` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `token` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `abilities` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `last_used_at` timestamp NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `ratings`
--

CREATE TABLE `ratings` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `stars` int(11) NOT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `location_id` bigint(20) UNSIGNED NOT NULL,
  `user_id` bigint(20) UNSIGNED NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- A tábla adatainak kiíratása `ratings`
--

INSERT INTO `ratings` (`id`, `stars`, `description`, `location_id`, `user_id`, `created_at`, `updated_at`) VALUES
(1, 4, 'Alice thought she might as well she might, what a long tail, certainly,\' said Alice in a great crowd assembled about them--all sorts of things--I can\'t remember half of them--and it belongs to the.', 3, 2, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(2, 1, 'I\'m not looking for the first figure,\' said the youth, \'one would hardly suppose That your eye was as long as I get it home?\' when it saw mine coming!\' \'How do you know the song, \'I\'d have said to.', 7, 4, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(3, 2, 'YET,\' she said to Alice, and tried to beat them off, and found that, as nearly as she ran; but the wise little Alice was not here before,\' said the Caterpillar. Alice said very humbly; \'I won\'t have.', 5, 5, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(4, 5, 'King said, turning to the end of the baby, it was a table, with a large canvas bag, which tied up at the jury-box, and saw that, in her life, and had to stop and untwist it. After a while, finding.', 6, 5, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(5, 1, 'King. \'Nothing whatever,\' said Alice. \'Call it what you mean,\' said Alice. \'Of course it is,\' said the Queen. \'Can you play croquet?\' The soldiers were always getting up and to her in a sorrowful.', 3, 2, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(6, 2, 'But her sister sat still and said \'What else had you to death.\"\' \'You are all pardoned.\' \'Come, THAT\'S a good opportunity for making her escape; so she tried the effect of lying down on their.', 9, 1, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(7, 2, 'Mock Turtle replied; \'and then the Rabbit\'s voice; and Alice was not much surprised at her with large round eyes, and half believed herself in a deep sigh, \'I was a large one, but it had finished.', 8, 1, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(8, 5, 'I say--that\'s the same side of the guinea-pigs cheered, and was gone in a mournful tone, \'he won\'t do a thing before, and she felt that there was no \'One, two, three, and away,\' but they all stopped.', 10, 2, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(9, 5, 'There was no more to be sure! However, everything is to-day! And yesterday things went on all the arches are gone from this side of the Mock Turtle, capering wildly about. \'Change lobsters again!\'.', 6, 3, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(10, 1, 'When the pie was all about, and make one quite giddy.\' \'All right,\' said the King. \'Nothing whatever,\' said Alice. \'And ever since that,\' the Hatter was out of the cupboards as she swam about.', 2, 1, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(11, 2, 'Duchess, the Duchess! Oh! won\'t she be savage if I\'ve kept her waiting!\' Alice felt so desperate that she still held the pieces of mushroom in her lessons in here? Why, there\'s hardly enough of it.', 8, 3, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(12, 3, 'Alice replied very politely, \'if I had our Dinah here, I know all sorts of little Alice herself, and nibbled a little girl she\'ll think me at all.\' \'In that case,\' said the Queen. \'You make me.', 6, 2, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(13, 4, 'I never knew whether it was certainly English. \'I don\'t believe there\'s an atom of meaning in it,\' but none of YOUR adventures.\' \'I could tell you just now what the next verse,\' the Gryphon went on.', 3, 5, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(14, 1, 'IT. It\'s HIM.\' \'I don\'t much care where--\' said Alice. \'Come, let\'s hear some of them even when they arrived, with a whiting. Now you know.\' \'I DON\'T know,\' said the King, \'and don\'t be nervous, or.', 7, 3, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(15, 3, 'Queen say only yesterday you deserved to be rude, so she turned to the table to measure herself by it, and kept doubling itself up and repeat \"\'TIS THE VOICE OF THE SLUGGARD,\"\' said the Cat. \'I said.', 8, 3, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(16, 3, 'Alice, \'it would have called him Tortoise because he was gone, and the constant heavy sobbing of the jury consider their verdict,\' the King said gravely, \'and go on in these words: \'Yes, we went to.', 9, 5, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(17, 2, 'March Hare. \'I didn\'t know it to be done, I wonder?\' As she said to herself, being rather proud of it: for she felt that this could not answer without a moment\'s delay would cost them their lives.', 2, 5, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(18, 1, 'Laughing and Grief, they used to say.\' \'So he did, so he did,\' said the Mock Turtle sighed deeply, and began, in a great letter, nearly as large as the Caterpillar took the regular course.\' \'What.', 5, 4, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(19, 3, 'There were doors all round the table, but it did not see anything that looked like the wind, and the three gardeners instantly threw themselves flat upon their faces, so that by the whole window!\'.', 9, 4, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(20, 1, 'I don\'t know,\' he went on at last, with a lobster as a partner!\' cried the Mouse, who was beginning to end,\' said the Duchess, \'as pigs have to turn into a pig, and she walked down the chimney close.', 2, 5, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(21, 3, 'Now you know.\' He was looking about for them, and it\'ll sit up and repeat \"\'TIS THE VOICE OF THE SLUGGARD,\"\' said the Cat, \'a dog\'s not mad. You grant that?\' \'I suppose they are the jurors.\' She.', 10, 2, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(22, 5, 'She did it at all. However, \'jury-men\' would have appeared to them she heard a little more conversation with her head!\' the Queen had ordered. They very soon had to do anything but sit with its.', 8, 1, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(23, 3, 'Dodo in an encouraging tone. Alice looked up, and began bowing to the jury, in a tone of this rope--Will the roof bear?--Mind that loose slate--Oh, it\'s coming down! Heads below!\' (a loud.', 10, 2, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(24, 2, 'Said he thanked the whiting kindly, but he now hastily began again, using the ink, that was sitting on the whole pack of cards: the Knave of Hearts, he stole those tarts, And took them quite away!\'.', 10, 2, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(25, 3, 'Queen put on one knee. \'I\'m a poor man, your Majesty,\' said Two, in a tone of great surprise. \'Of course they were\', said the Dormouse: \'not in that poky little house, on the breeze that followed.', 4, 2, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(26, 5, 'Queen in front of the Rabbit\'s voice along--\'Catch him, you by the Queen jumped up on tiptoe, and peeped over the wig, (look at the end of your flamingo. Shall I try the thing Mock Turtle to sing.', 5, 2, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(27, 1, 'Gryphon. \'How the creatures order one about, and crept a little bottle that stood near. The three soldiers wandered about for it, she found her head made her next remark. \'Then the eleventh day must.', 2, 2, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(28, 5, 'I think.\' And she began nursing her child again, singing a sort of lullaby to it in her life, and had just succeeded in curving it down \'important,\' and some \'unimportant.\' Alice could not even room.', 7, 1, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(29, 5, 'This time there were three gardeners at it, busily painting them red. Alice thought to herself. \'I dare say there may be ONE.\' \'One, indeed!\' said the Cat, \'a dog\'s not mad. You grant that?\' \'I.', 3, 2, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(30, 5, 'March Hare: she thought it had struck her foot! She was walking hand in hand, in couples: they were nice grand words to say.) Presently she began shrinking directly. As soon as it is.\' \'Then you may.', 3, 5, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(31, 4, 'Alice could speak again. In a little girl,\' said Alice, as she was playing against herself, for this time the Queen in a voice outside, and stopped to listen. \'Mary Ann! Mary Ann!\' said the Queen.', 7, 2, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(32, 2, 'When she got up in spite of all this time. \'I want a clean cup,\' interrupted the Hatter: \'it\'s very rude.\' The Hatter was out of the suppressed guinea-pigs, filled the air, mixed up with the words.', 9, 4, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(33, 2, 'Mystery,\' the Mock Turtle. \'Seals, turtles, salmon, and so on.\' \'What a curious dream!\' said Alice, rather doubtfully, as she could. \'The Dormouse is asleep again,\' said the Caterpillar. \'Well.', 8, 4, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(34, 5, 'Alice, very earnestly. \'I\'ve had nothing yet,\' Alice replied in a voice she had wept when she had gone through that day. \'A likely story indeed!\' said the King: \'leave out that she was quite pleased.', 10, 5, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(35, 5, 'Alice; but she was walking by the little golden key, and unlocking the door opened inwards, and Alice\'s first thought was that you had been (Before she had plenty of time as she stood looking at it.', 8, 3, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(36, 2, 'Caterpillar took the hookah out of sight: then it chuckled. \'What fun!\' said the Lory, who at last it sat down again into its eyes were looking up into hers--she could hear him sighing as if he.', 1, 5, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(37, 5, 'Oh dear! I\'d nearly forgotten to ask.\' \'It turned into a tree. By the use of this sort in her haste, she had never heard it say to this: so she helped herself to about two feet high: even then she.', 9, 1, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(38, 2, 'Alice was very like a thunderstorm. \'A fine day, your Majesty!\' the soldiers remaining behind to execute the unfortunate gardeners, who ran to Alice a good deal on where you want to go! Let me see.', 2, 2, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(39, 4, 'This did not seem to put it in less than a real Turtle.\' These words were followed by a row of lodging houses, and behind it was all very well to say \"HOW DOTH THE LITTLE BUSY BEE,\" but it puzzled.', 3, 3, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(40, 1, 'I wish you wouldn\'t mind,\' said Alice: \'she\'s so extremely--\' Just then her head pressing against the ceiling, and had no idea what you\'re doing!\' cried Alice, with a knife, it usually bleeds; and.', 2, 4, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(41, 2, 'Alice thought this must be what he did it,) he did with the edge of her age knew the meaning of half an hour or so, and giving it a bit, if you like,\' said the King. \'I can\'t help that,\' said the.', 6, 1, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(42, 5, 'Mock Turtle in the pool, \'and she sits purring so nicely by the little magic bottle had now had its full effect, and she trembled till she got into a pig, and she had forgotten the words.\' So they.', 3, 4, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(43, 2, 'Alice could hardly hear the Rabbit began. Alice thought to herself, as she had a bone in his confusion he bit a large cauldron which seemed to have any pepper in that poky little house, and have.', 10, 2, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(44, 2, 'Who for such dainties would not join the dance? Will you, won\'t you, won\'t you, will you, won\'t you join the dance?\"\' \'Thank you, sir, for your interesting story,\' but she had drunk half the bottle.', 7, 3, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(45, 2, 'Footman remarked, \'till tomorrow--\' At this moment the door between us. For instance, suppose it doesn\'t matter a bit,\' said the Gryphon. \'I\'ve forgotten the little creature down, and felt quite.', 2, 3, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(46, 1, 'The players all played at once in the sand with wooden spades, then a great deal too flustered to tell you--all I know is, something comes at me like that!\' \'I couldn\'t help it,\' said the Eaglet. \'I.', 5, 1, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(47, 3, 'Cat. \'I said pig,\' replied Alice; \'and I do wonder what Latitude was, or Longitude I\'ve got back to the waving of the birds and animals that had a large rabbit-hole under the circumstances. There.', 1, 5, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(48, 3, 'Now, if you wouldn\'t mind,\' said Alice: \'I don\'t know what to do it?\' \'In my youth,\' said his father, \'I took to the voice of the evening, beautiful Soup! Beau--ootiful Soo--oop! Beau--ootiful.', 10, 2, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(49, 3, 'Who would not stoop? Soup of the lefthand bit of mushroom, and crawled away in the wood, \'is to grow here,\' said the Hatter, with an anxious look at them--\'I wish they\'d get the trial one way up as.', 10, 1, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(50, 3, 'THAT direction,\' the Cat said, waving its right paw round, \'lives a March Hare. \'Yes, please do!\' but the three gardeners, but she ran off at once crowded round it, panting, and asking, \'But who is.', 10, 1, '2022-04-19 16:15:15', '2022-04-19 16:15:15');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `users`
--

CREATE TABLE `users` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `username` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `permission` tinyint(4) NOT NULL DEFAULT 0,
  `remember_token` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- A tábla adatainak kiíratása `users`
--

INSERT INTO `users` (`id`, `username`, `email`, `password`, `permission`, `remember_token`, `created_at`, `updated_at`) VALUES
(1, 'superadmin', 'superadmin@example.com', '$2y$10$ZJd.HImB/5K0Zm8nmmCt7uF9.JCcwVudMeZViANmhsDQ/NCSoOKZK', 3, NULL, '2022-04-19 16:15:14', '2022-04-19 16:15:14'),
(2, 'admin', 'admin@example.com', '$2y$10$ucMF22XJyfVWEZ/UgR2cIO3zZf8o/bszk9Dj.qOchY5vsN36bGOOW', 2, NULL, '2022-04-19 16:15:14', '2022-04-19 16:15:14'),
(3, 'test_user_1', 'test_user_1@example.com', '$2y$10$iDIgGPY6eNPD8F0Qbg.kAO1vTFEsmi3E4XOzqj7oRY5/cFJbaZNBu', 0, NULL, '2022-04-19 16:15:14', '2022-04-19 16:15:14'),
(4, 'test_user_2', 'test_user_2@example.com', '$2y$10$WEstQIVWiDMplQXGs.gtIOC6YZzt4zXDZQts42poDtVC4GKP4qxF2', 0, NULL, '2022-04-19 16:15:15', '2022-04-19 16:15:15'),
(5, 'test_user_banned', 'test_user_banned@example.com', '$2y$10$lG0msubT35k1yFdBxMetp.ROifyCjFocb8OjeBO5jG4DyBGEY8joW', 1, NULL, '2022-04-19 16:15:15', '2022-04-19 16:15:15');

--
-- Indexek a kiírt táblákhoz
--

--
-- A tábla indexei `feedback`
--
ALTER TABLE `feedback`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `locations`
--
ALTER TABLE `locations`
  ADD PRIMARY KEY (`id`),
  ADD KEY `locations_user_id_foreign` (`user_id`);

--
-- A tábla indexei `migrations`
--
ALTER TABLE `migrations`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `personal_access_tokens`
--
ALTER TABLE `personal_access_tokens`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `personal_access_tokens_token_unique` (`token`),
  ADD KEY `personal_access_tokens_tokenable_type_tokenable_id_index` (`tokenable_type`,`tokenable_id`);

--
-- A tábla indexei `ratings`
--
ALTER TABLE `ratings`
  ADD PRIMARY KEY (`id`),
  ADD KEY `ratings_location_id_foreign` (`location_id`),
  ADD KEY `ratings_user_id_foreign` (`user_id`);

--
-- A tábla indexei `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `users_username_unique` (`username`),
  ADD UNIQUE KEY `users_email_unique` (`email`);

--
-- A kiírt táblák AUTO_INCREMENT értéke
--

--
-- AUTO_INCREMENT a táblához `feedback`
--
ALTER TABLE `feedback`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT a táblához `locations`
--
ALTER TABLE `locations`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT a táblához `migrations`
--
ALTER TABLE `migrations`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT a táblához `personal_access_tokens`
--
ALTER TABLE `personal_access_tokens`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT a táblához `ratings`
--
ALTER TABLE `ratings`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- AUTO_INCREMENT a táblához `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Megkötések a kiírt táblákhoz
--

--
-- Megkötések a táblához `locations`
--
ALTER TABLE `locations`
  ADD CONSTRAINT `locations_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Megkötések a táblához `ratings`
--
ALTER TABLE `ratings`
  ADD CONSTRAINT `ratings_location_id_foreign` FOREIGN KEY (`location_id`) REFERENCES `locations` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `ratings_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
