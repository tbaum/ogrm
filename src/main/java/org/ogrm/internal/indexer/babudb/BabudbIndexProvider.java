package org.ogrm.internal.indexer.babudb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.ogrm.PersistenceException;
import org.ogrm.config.Configuration;
import org.ogrm.logger.Logger;
import org.ogrm.logger.LoggerFactory;
import org.ogrm.util.Maps;
import org.xtreemfs.babudb.BabuDB;
import org.xtreemfs.babudb.BabuDBException;
import org.xtreemfs.babudb.BabuDBFactory;
import org.xtreemfs.babudb.BabuDBException.ErrorCode;
import org.xtreemfs.babudb.config.BabuDBConfig;
import org.xtreemfs.babudb.log.DiskLogger.SyncMode;
import org.xtreemfs.babudb.lsmdb.Database;
import org.xtreemfs.babudb.lsmdb.DatabaseManager;

public class BabudbIndexProvider {

	private static Logger logger = LoggerFactory.getLogger( BabudbIndexProvider.class );

	private static final String LOCKFILE = "babudb.lck";

	private Map<String, BabuDbIndex> indexes;

	private DatabaseManager manager;

	private String directory;

	private boolean overwrite;

	private BabuDB babudb;

	private Configuration config;

	public static BabudbIndexProvider createIndex( Configuration config, String directory ) {
		return new BabudbIndexProvider( directory, config, false );
	}

	public static BabudbIndexProvider createAndOverwriteIndex( Configuration config, String directory ) {
		return new BabudbIndexProvider( directory, config, true );
	}

	private BabudbIndexProvider(String directory, Configuration config, boolean overwrite) {
		indexes = Maps.newMap();
		this.overwrite = overwrite;
		this.directory = directory;
		this.config = config;
		File baseDir = new File( directory );
		checkFile( baseDir );
	}

	public BabuDbIndex getIndex( String name ) {
		BabuDbIndex index = indexes.get( name );
		if (index == null) {
			synchronized (indexes) {
				if (indexes.get( name ) == null) {
					index = new BabuDbIndex( getDatabase( name ), config.getGraph() );
					indexes.put( name, index );
				}
			}
		}
		return index;
	}

	private Database getDatabase( String name ) {

		File baseDir = new File( directory );

		File db = new File( baseDir, "db" );
		db.mkdirs();

		File log = new File( baseDir, "log" );
		log.mkdirs();

		Database database = null;

		try {

			if (babudb == null) {
//				makeLockFile();

				int numOfWorkerThreads = 2;

				long maxLogFileSize = 1024 * 1024;

				int checkIntervalInSecs = 10;

				int pseudoSyncWaitInMillis = 0;

				int maxQueueSize = 0;

				boolean useCompression = false;

				int maxEntriesPerBlock = 1000000;

				int maxBlockSize = 536870912;

				babudb = BabuDBFactory.createBabuDB( new BabuDBConfig( db.getAbsolutePath(), log.getAbsolutePath(),
						numOfWorkerThreads, maxLogFileSize, checkIntervalInSecs, SyncMode.FSYNC,
						pseudoSyncWaitInMillis, maxQueueSize, useCompression, maxEntriesPerBlock, maxBlockSize ) );

				manager = babudb.getDatabaseManager();
			}
			try {
				database = manager.createDatabase( name, 1 );
				logger.info( "%s: creating new database for %s at %s", this, name, baseDir.getAbsolutePath() );
			} catch (BabuDBException e) {
				if (e.getErrorCode().equals( ErrorCode.DB_EXISTS )) {
					logger.info( "Database for %s is present at %s", name, baseDir.getAbsolutePath() );
					database = manager.getDatabase( name );
				} else {
					throw e;
				}
			}
		} catch (Throwable e) {
			logger.error( e, "Error while creating db" );
			throw new PersistenceException( "Could not create database at " + baseDir.getAbsolutePath() + " for "
					+ name, e );
		}
		return database;
	}

	private void checkFile( File file ) {
		try {
			if (!file.exists()) {
				file.mkdirs();
				System.out.println( "BabuDbStore.checkFile " + file + " created" );
			} else if (overwrite) {
				deleteRecursive( file );
				file.mkdirs();
				System.out.println( "BabuDbStore.checkFile " + file + " created" );
			}
		} catch (Exception e) {
			throw new PersistenceException( "Could not create file " + file );
		}
	}

	private void clearLockFile() {
		File baseDir = new File( directory );
		File lockFile = new File( baseDir, LOCKFILE );
		System.out.println( "BabuDbStore.clearLockFile Deleting lockfile" );
		lockFile.delete();
	}

	private void makeLockFile() {
		logger.debug( "%s checking lockfile", this );
		File baseDir = new File( directory );
		File lockFile = new File( baseDir, LOCKFILE );
		try {
			if (!lockFile.createNewFile()) {
				String info = readInfoInLockfile( lockFile );
				throw new PersistenceException( this + ": the database is in use by another process ( " + info
						+ " ) . If this is incorrect, delete the lockfile " + lockFile.getCanonicalPath() );
			} else {
				writeInfoToLockFile( lockFile );
			}
		} catch (IOException e) {
			throw new PersistenceException( "Could not create lockfile " + lockFile.getPath(), e );
		}
	}

	private void writeInfoToLockFile( File file ) {
		try {
			FileWriter outFile = new FileWriter( file );
			PrintWriter out = new PrintWriter( outFile );

			// Also could be written as follows on one line
			// Printwriter out = new PrintWriter(new FileWriter(args[0]));

			// Write text to file
			out.println( "Created " + new Date().toString() + " by " + this.toString() );
			out.close();
		} catch (IOException e) {
			logger.warn( "Could not write info into lockfile" );
		}
	}

	private String readInfoInLockfile( File file ) {
		try {
			BufferedReader reader = new BufferedReader( new FileReader( file ) );
			return reader.readLine();
		} catch (IOException e) {
			logger.warn( "Could not write info into lockfile" );
		}
		return "";
	}

	private void deleteRecursive( File file ) {
		if (file.isDirectory()) {
			for (File child : file.listFiles()) {
				deleteRecursive( child );
			}
		}
		System.out.println( "BabuDbStore.deleteRecursive Deleting file " + file );
		file.delete();
	}

	public synchronized void dispose() {

		// manager.getCheckpointer().checkpoint();
		try {
			if (babudb != null)
				babudb.shutdown();
			clearLockFile();
		} catch (BabuDBException e) {
			logger.error( e, "Error while shutting down databases" );
		}

	}

	@Override
	public String toString() {
		return new ToStringBuilder( this, ToStringStyle.SHORT_PREFIX_STYLE ).append( "directory", directory ).append(
				"hashcode", hashCode() ).toString();
	}

}
