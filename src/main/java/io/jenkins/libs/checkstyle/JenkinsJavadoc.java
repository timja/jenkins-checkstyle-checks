package io.jenkins.libs.checkstyle;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Checks that the javadoc comments follow Jenkins conventions.
 *
 */
public class JenkinsJavadoc extends AbstractCheck {

	public static final int[] NO_REQUIRED_TOKENS = {};

	private static final Pattern SINCE_TAG_PATTERN = Pattern.compile("@since\\s+(.*)");

	private static final Set<Integer> TOP_LEVEL_TYPES;
	static {
		Set<Integer> topLevelTypes = new HashSet<>();
		topLevelTypes.add(TokenTypes.INTERFACE_DEF);
		topLevelTypes.add(TokenTypes.CLASS_DEF);
		topLevelTypes.add(TokenTypes.METHOD_DEF);
		topLevelTypes.add(TokenTypes.ENUM_DEF);
		topLevelTypes.add(TokenTypes.ANNOTATION_DEF);
		TOP_LEVEL_TYPES = Collections.unmodifiableSet(topLevelTypes);
	}

	private Map<Integer, TextBlock> blockComments;

	@Override
	public int[] getDefaultTokens() {
		return new int[] { TokenTypes.INTERFACE_DEF, TokenTypes.CLASS_DEF, TokenTypes.ENUM_DEF,
				TokenTypes.ANNOTATION_DEF, TokenTypes.METHOD_DEF, TokenTypes.CTOR_DEF };
	}

	@Override
	public int[] getAcceptableTokens() {
		return new int[] { TokenTypes.INTERFACE_DEF, TokenTypes.CLASS_DEF, TokenTypes.ENUM_DEF,
				TokenTypes.ANNOTATION_DEF, TokenTypes.METHOD_DEF, TokenTypes.CTOR_DEF, TokenTypes.ENUM_CONSTANT_DEF,
				TokenTypes.ANNOTATION_FIELD_DEF };
	}

	@Override
	public int[] getRequiredTokens() {
		return NO_REQUIRED_TOKENS;
	}

	@Override
	public void beginTree(DetailAST rootAST) {
		super.beginTree(rootAST);
		this.blockComments = new HashMap<>();
		FileContents contents = getFileContents();
		for (List<TextBlock> blockComments : contents.getBlockComments().values()) {
			for (TextBlock blockComment : blockComments) {
				this.blockComments.put(blockComment.getEndLineNo(), blockComment);
			}
		}
	}

	@Override
	public void visitToken(DetailAST ast) {
		int lineNumber = ast.getLineNo();
		TextBlock javadoc = getFileContents().getJavadocBefore(lineNumber);
		if (javadoc != null) {
			checkSinceTag(ast, javadoc);
		}
	}

	private void checkSinceTag(DetailAST ast, TextBlock javadoc) {
		if (!TOP_LEVEL_TYPES.contains(ast.getType())) {
			return;
		}
		String[] text = javadoc.getText();
		DetailAST interfaceDef = getInterfaceDef(ast);
		boolean privateType = !isPublicOrProtected(ast) && (interfaceDef == null || !isPublicOrProtected(interfaceDef));
		boolean found = false;
		for (int i = 0; i < text.length; i++) {
			Matcher matcher = SINCE_TAG_PATTERN.matcher(text[i]);
			if (matcher.find()) {
				found = true;
				String description = matcher.group(1).trim();
				if (privateType) {
					log(javadoc.getStartLineNo() + i, text[i].length() - description.length(), "javadoc.publicSince");
				}
			}
		}
		if (!found && !privateType) {
			log(javadoc.getStartLineNo(), 0, "javadoc.missingSince");
		}
	}

	private DetailAST getInterfaceDef(DetailAST ast) {
		return findParent(ast, TokenTypes.INTERFACE_DEF);
	}

	private DetailAST findParent(DetailAST ast, int classDef) {
		while (ast != null) {
			if (ast.getType() == classDef) {
				return ast;
			}
			ast = ast.getParent();
		}
		return null;
	}

	private boolean isPublicOrProtected(DetailAST ast) {
		DetailAST modifiers = ast.findFirstToken(TokenTypes.MODIFIERS);
		if (modifiers == null) {
			return false;
		}
		return modifiers.findFirstToken(TokenTypes.LITERAL_PUBLIC) != null
				|| modifiers.findFirstToken(TokenTypes.LITERAL_PROTECTED) != null;
	}

}